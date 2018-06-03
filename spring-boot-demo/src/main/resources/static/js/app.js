var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/endpointWisely');//连接sockJS的endpoint名称为"/endpointWisely"
    stompClient = Stomp.over(socket);//使用STOMP子协议的websocket客户端
    stompClient.connect({}, function (frame) {//连接WebSocket服务端
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/getResponse', function (greeting) {//通过stompClient.subscribe订阅/topic/getResponse目标发送的消息
            showGreeting(JSON.parse(greeting.body).responseMessage);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/message/say", {}, JSON.stringify({'message': $("#name").val()}));//向服务端发送消息
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});