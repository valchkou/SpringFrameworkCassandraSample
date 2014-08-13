(function(){
	var self = {};
	var restApi = {baseUrl:"/spring-sample/api"};
	
	window.onload = function(e){ 
		self.init();
	};	
		
	self.init = function() {
		
		// Disable caching of AJAX responses
		$.ajaxSetup({cache : false});
		
		// ${} pattern for underscore templates
		_.templateSettings = {interpolate : /\$\{(.+?)\}/g};
		
		// prepare templates
		self.messageTemplate = _.template( $("#message_template").html());
		
		$("#postButton").on("click", self.sendMessage);
		$("#messageText").on("keypress", self.messageTextPressed);
		
		// imitate user login
		restApi.getUser(self.getUserSuccess, self.connectionError);
	};
	
	// post message if enter pressed
	self.messageTextPressed = function(event) {
		if (event.keyCode == 13) {
			self.sendMessage(event);
		}
	};
	
	self.sendMessage = function(event) {
		var text = $('#messageText').val();
		$('#messageText').val("");
		if (text == "" || text==undefined) {
			return;
		}
		var obj = new Message();
		obj.text = text;
		obj.author = self.user;
		self.renderMessage(obj);
		self.postMessage(text);
	};

	self.renderMessage = function (message) {
		// format message from template
		var html = self.messageTemplate(message.toUI());
		
		// append to DOM
		$('#messages').append(html);
	};
		
	/** send message on server */
	self.postMessage = function(message) {
		var onSuccess = function(data) {
			self.postMessageSuccess(data, message);
		};
		restApi.post(self.user, message, onSuccess, self.connectionError);
	};

	self.postMessageSuccess = function(data, message) {
		// message posted successfully
	};

	
	/** read messages from server */
	self.pollMessages = function() {
		restApi.readMessages(self.user, self.lastId, self.pollSuccess, self.connectionError);
	};
	
	/** successfully retrieved messages from server */
	self.pollSuccess = function(data) {
		var messages= data;
		if (messages) {
			for ( var i = 0; i < messages.length; i++) {
				var obj = messages[i];
				var message = Message.fromJson(obj);
				self.lastId = message.id;
				self.renderMessage(message);
			};
		};	
	};
	
	self.connectionError = function (data) {
		console.log(" Can't connect to service");
	};
	
	/** successfully retrieved user from the server */
	self.getUserSuccess = function(data) {
		self.user = data;
		$("#userName").text("I am " + self.user);
		self.lastId = null;
		
		// start short polling the messages posted by other users
		self.timer = setInterval(self.pollMessages, 5000);
	};
	
	/******************** REST service api definiton **************************/

	/**
	 * get current user from the server
	 */
	restApi.getUser = function(onSuccess, onError) {
		$.ajax({
			url : restApi.baseUrl+"/messages/user",
			dataType :"text",
			contentType: "application/text; charset=UTF-8"
		})
		.done(onSuccess)
	    .fail(onError);
	};

	
	/** post a message */
	restApi.post = function(name, text, onSuccess, onError) {
		$.ajax({
			url : restApi.baseUrl+"/messages/user/"+name,
			type: "POST",
			data : text,
			dataType :"text",
			contentType: "application/text; charset=UTF-8"
		})		
		.done(onSuccess)
	    .fail(onError);
	};

	/** get all posted messages */
	restApi.readMessages = function(name, lastId, onSuccess, onError) {
		var url = restApi.baseUrl+"/messages/user/"+name;
		if (lastId) {
			url = url+"/"+lastId;
		}
		$.ajax({
			url : url,
			type: "GET",
			dataType : "json"
		})      
		.done(onSuccess)
	    .fail(onError);
	};	
	
	/************************ Define Message Object ****************************/ 
	function Message() {
		var ts = moment.utc();
		this.id = ts.unix();
		this.timestamp = ts;
		this.text = "";
		this.author = self.user;
	}
		
	/**
	 * function to instantiate Message from json obj
	 * usage: var message = Message.fronJson(json)
	 */
	Message.fromJson = function(jsonObj) {
		var obj = new Message();
		obj.id = jsonObj.key.id;
		obj.author = jsonObj.key.userName;
		obj.text = jsonObj.text;
		obj.timestamp = moment.utc(jsonObj.key.timestamp);
		return obj;
	};
	
	/**	return formatted obj for ui */
	Message.prototype.toUI = function() {
			var obj = {};
			obj.id = this.id;
			obj.author = this.author;
			obj.text = this.text;
			obj.timestamp = this.timestamp.format('HH:mm:ss');
			return obj;			
	};	
	
})();