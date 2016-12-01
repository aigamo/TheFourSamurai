var HelloStomp = function() {
    this.connectButton = document.getElementById('connect');
    this.disconnectButton = document.getElementById('disconnect');
    // イベントハンドラの登録
    this.connectButton.addEventListener('click', this.connect.bind(this));
    this.disconnectButton.addEventListener('click', this.disconnect.bind(this));

};

/**
 * エンドポイントへの接続処理
 */
HelloStomp.prototype.connect = function() {
    var socket = new SockJS('/endpoint'); // エンドポイントのURL
    this.stompClient = Stomp.over(socket); // WebSocketを使ったStompクライアントを作成
    this.stompClient.debug = null;
    this.stompClient.connect({}, this.onConnected.bind(this)); // エンドポイントに接続し、接続した際のコールバックを登録
};

/**
 * エンドポイントへ接続したときの処理
 */
HelloStomp.prototype.onConnected = function(frame) {
    console.log('Connected: ' + frame);
    // 宛先が'/topic/greetings'のメッセージを購読し、コールバック処理を登録
    this.stompClient.subscribe('/topic/sounddata', this.onSubscribeGreeting
            .bind(this));
    this.setConnected(true);
};

/**
 * ボタン表示の切り替え
 */
HelloStomp.prototype.setConnected = function(connected) {
    this.connectButton.disabled = connected;
    this.disconnectButton.disabled = !connected;
};
/**
 * 宛先'/topic/greetings'なメッセージを受信したときの処理
 */
HelloStomp.prototype.onSubscribeGreeting = function(message) {
    this.dataArray = [ [ 'date', 'LaughRate' ] ];

    this.drowChart(message.body);
};

/**
 * 接続切断処理
 */
HelloStomp.prototype.disconnect = function() {
    if (this.stompClient) {
        this.stompClient.disconnect();
        this.stompClient = null;
    }
    this.setConnected(false);
};

HelloStomp.prototype.dataArray = [ [] ];

var dataArray = [ ];

HelloStomp.prototype.initChart = function() {
    // var df = $.Deferred();

    this.dataArray = [ [ 'date', 'LaughRate' ] ];
    //for (var i = 0; i < 3; i++) {
    //    this.dataArray.push([ 0, 0 ]);
   // }
    var options = {
        title : 'Laugh Graph',
        hAxis : {
            title : 'わら点',
            titleTextStyle : {
                color : '#333'
            },
            minValue : 0,
            maxValue : 10000
        },
        vAxis : {
            minValue : 0,
            maxValue : 1000
        }

    };
    var chart = new google.visualization.LineChart(document
            .getElementById('chart_div'));

    var chartdata = google.visualization.arrayToDataTable(this.dataArray);

    chart.draw(chartdata, options);

};

HelloStomp.prototype.drowChart = function(message) {
    var data = JSON.parse(message);

    var data_item = [ parseInt(data.now), parseInt(data.data) ];
    dataArray.push(data_item);
    //dataArray.shift();

    var chartdata = google.visualization.arrayToDataTable(dataArray);

    var chart = new google.visualization.AreaChart(document
            .getElementById('chart_div'));

    var options = {
        title : 'Laugh Graph',
        hAxis : {
            title : 'Time',
            titleTextStyle : {
                color : '#333'
            },
        },
        vAxis : {
            minValue : 0,
            maxValue : 3000
        }

    };
    chart.draw(chartdata, options);
};

(function() {
    google.load("visualization", "1", {
        packages : [ "corechart" ]
    });
    var stomp = new HelloStomp();
    google.setOnLoadCallback(stomp.initChart);
    stomp.connect();

}());
