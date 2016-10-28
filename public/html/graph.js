      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);

      function drawChart() {
      var dataArray = [['Boke&Tsukkomi','Laugh Rate']];
      var df = $.Deferred();

      $(function() {
          $.ajax({
          url: '../data/test.json',
          dataType : 'json',
          cache : false
          }).done(function(data){
          console.log("success");
          $(data.test).each(function(){
              var data_item = [this.boketsukkomi,this.laughrate];
              dataArray.push(data_item);
          });
          df.resolve();
          }).fail(function(){
          console.log("error");
          });
      });

      df.done(function(){
      var chartdata = google.visualization.arrayToDataTable(dataArray);

      var options = {
			  title: 'Laugh Graph'
          };
          var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
          chart.draw(chartdata, options);
      });
      }