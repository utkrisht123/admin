var Index = function () {
	var st,ed;
    return {

        //main function
        init: function () {
            Metronic.addResizeHandler(function () {
                jQuery('.vmaps').each(function () {
                    var map = jQuery(this);
                    map.width(map.parent().width());
                });
            });
        },

 initJQVMAP: function () {
        	
        	/*
        	although ammap has methos like getAreaCenterLatitude and getAreaCenterLongitude,
        	they are not suitable in quite a lot of cases as the center of some countries
        	is even outside the country itself (like US, because of Alaska and Hawaii)
        	That's why wehave the coordinates stored here
        */
        	$('#region_statistics_loading').show();

        var latlong = {};
        latlong["AD"] = {"latitude":42.5, "longitude":1.5};
        latlong["AE"] = {"latitude":24, "longitude":54};
        latlong["AF"] = {"latitude":33, "longitude":65};
        latlong["AG"] = {"latitude":17.05, "longitude":-61.8};
        latlong["AI"] = {"latitude":18.25, "longitude":-63.1667};
        latlong["AL"] = {"latitude":41, "longitude":20};
        latlong["AM"] = {"latitude":40, "longitude":45};
        latlong["AN"] = {"latitude":12.25, "longitude":-68.75};
        latlong["AO"] = {"latitude":-12.5, "longitude":18.5};
        latlong["AP"] = {"latitude":35, "longitude":105};
        latlong["AQ"] = {"latitude":-90, "longitude":0};
        latlong["AR"] = {"latitude":-34, "longitude":-64};
        latlong["AS"] = {"latitude":-14.3333, "longitude":-170};
        latlong["AT"] = {"latitude":47.3333, "longitude":13.3333};
        latlong["AU"] = {"latitude":-27, "longitude":133};
        latlong["AW"] = {"latitude":12.5, "longitude":-69.9667};
        latlong["AZ"] = {"latitude":40.5, "longitude":47.5};
        latlong["BA"] = {"latitude":44, "longitude":18};
        latlong["BB"] = {"latitude":13.1667, "longitude":-59.5333};
        latlong["BD"] = {"latitude":24, "longitude":90};
        latlong["BE"] = {"latitude":50.8333, "longitude":4};
        latlong["BF"] = {"latitude":13, "longitude":-2};
        latlong["BG"] = {"latitude":43, "longitude":25};
        latlong["BH"] = {"latitude":26, "longitude":50.55};
        latlong["BI"] = {"latitude":-3.5, "longitude":30};
        latlong["BJ"] = {"latitude":9.5, "longitude":2.25};
        latlong["BM"] = {"latitude":32.3333, "longitude":-64.75};
        latlong["BN"] = {"latitude":4.5, "longitude":114.6667};
        latlong["BO"] = {"latitude":-17, "longitude":-65};
        latlong["BR"] = {"latitude":-10, "longitude":-55};
        latlong["BS"] = {"latitude":24.25, "longitude":-76};
        latlong["BT"] = {"latitude":27.5, "longitude":90.5};
        latlong["BV"] = {"latitude":-54.4333, "longitude":3.4};
        latlong["BW"] = {"latitude":-22, "longitude":24};
        latlong["BY"] = {"latitude":53, "longitude":28};
        latlong["BZ"] = {"latitude":17.25, "longitude":-88.75};
        latlong["CA"] = {"latitude":54, "longitude":-100};
        latlong["CC"] = {"latitude":-12.5, "longitude":96.8333};
        latlong["CD"] = {"latitude":0, "longitude":25};
        latlong["CF"] = {"latitude":7, "longitude":21};
        latlong["CG"] = {"latitude":-1, "longitude":15};
        latlong["CH"] = {"latitude":47, "longitude":8};
        latlong["CI"] = {"latitude":8, "longitude":-5};
        latlong["CK"] = {"latitude":-21.2333, "longitude":-159.7667};
        latlong["CL"] = {"latitude":-30, "longitude":-71};
        latlong["CM"] = {"latitude":6, "longitude":12};
        latlong["CN"] = {"latitude":35, "longitude":105};
        latlong["CO"] = {"latitude":4, "longitude":-72};
        latlong["CR"] = {"latitude":10, "longitude":-84};
        latlong["CU"] = {"latitude":21.5, "longitude":-80};
        latlong["CV"] = {"latitude":16, "longitude":-24};
        latlong["CX"] = {"latitude":-10.5, "longitude":105.6667};
        latlong["CY"] = {"latitude":35, "longitude":33};
        latlong["CZ"] = {"latitude":49.75, "longitude":15.5};
        latlong["DE"] = {"latitude":51, "longitude":9};
        latlong["DJ"] = {"latitude":11.5, "longitude":43};
        latlong["DK"] = {"latitude":56, "longitude":10};
        latlong["DM"] = {"latitude":15.4167, "longitude":-61.3333};
        latlong["DO"] = {"latitude":19, "longitude":-70.6667};
        latlong["DZ"] = {"latitude":28, "longitude":3};
        latlong["EC"] = {"latitude":-2, "longitude":-77.5};
        latlong["EE"] = {"latitude":59, "longitude":26};
        latlong["EG"] = {"latitude":27, "longitude":30};
        latlong["EH"] = {"latitude":24.5, "longitude":-13};
        latlong["ER"] = {"latitude":15, "longitude":39};
        latlong["ES"] = {"latitude":40, "longitude":-4};
        latlong["ET"] = {"latitude":8, "longitude":38};
        latlong["EU"] = {"latitude":47, "longitude":8};
        latlong["FI"] = {"latitude":62, "longitude":26};
        latlong["FJ"] = {"latitude":-18, "longitude":175};
        latlong["FK"] = {"latitude":-51.75, "longitude":-59};
        latlong["FM"] = {"latitude":6.9167, "longitude":158.25};
        latlong["FO"] = {"latitude":62, "longitude":-7};
        latlong["FR"] = {"latitude":46, "longitude":2};
        latlong["GA"] = {"latitude":-1, "longitude":11.75};
        latlong["GB"] = {"latitude":54, "longitude":-2};
        latlong["GD"] = {"latitude":12.1167, "longitude":-61.6667};
        latlong["GE"] = {"latitude":42, "longitude":43.5};
        latlong["GF"] = {"latitude":4, "longitude":-53};
        latlong["GH"] = {"latitude":8, "longitude":-2};
        latlong["GI"] = {"latitude":36.1833, "longitude":-5.3667};
        latlong["GL"] = {"latitude":72, "longitude":-40};
        latlong["GM"] = {"latitude":13.4667, "longitude":-16.5667};
        latlong["GN"] = {"latitude":11, "longitude":-10};
        latlong["GP"] = {"latitude":16.25, "longitude":-61.5833};
        latlong["GQ"] = {"latitude":2, "longitude":10};
        latlong["GR"] = {"latitude":39, "longitude":22};
        latlong["GS"] = {"latitude":-54.5, "longitude":-37};
        latlong["GT"] = {"latitude":15.5, "longitude":-90.25};
        latlong["GU"] = {"latitude":13.4667, "longitude":144.7833};
        latlong["GW"] = {"latitude":12, "longitude":-15};
        latlong["GY"] = {"latitude":5, "longitude":-59};
        latlong["HK"] = {"latitude":22.25, "longitude":114.1667};
        latlong["HM"] = {"latitude":-53.1, "longitude":72.5167};
        latlong["HN"] = {"latitude":15, "longitude":-86.5};
        latlong["HR"] = {"latitude":45.1667, "longitude":15.5};
        latlong["HT"] = {"latitude":19, "longitude":-72.4167};
        latlong["HU"] = {"latitude":47, "longitude":20};
        latlong["ID"] = {"latitude":-5, "longitude":120};
        latlong["IE"] = {"latitude":53, "longitude":-8};
        latlong["IL"] = {"latitude":31.5, "longitude":34.75};
        latlong["IN"] = {"latitude":20, "longitude":77};
        latlong["IO"] = {"latitude":-6, "longitude":71.5};
        latlong["IQ"] = {"latitude":33, "longitude":44};
        latlong["IR"] = {"latitude":32, "longitude":53};
        latlong["IS"] = {"latitude":65, "longitude":-18};
        latlong["IT"] = {"latitude":42.8333, "longitude":12.8333};
        latlong["JM"] = {"latitude":18.25, "longitude":-77.5};
        latlong["JO"] = {"latitude":31, "longitude":36};
        latlong["JP"] = {"latitude":36, "longitude":138};
        latlong["KE"] = {"latitude":1, "longitude":38};
        latlong["KG"] = {"latitude":41, "longitude":75};
        latlong["KH"] = {"latitude":13, "longitude":105};
        latlong["KI"] = {"latitude":1.4167, "longitude":173};
        latlong["KM"] = {"latitude":-12.1667, "longitude":44.25};
        latlong["KN"] = {"latitude":17.3333, "longitude":-62.75};
        latlong["KP"] = {"latitude":40, "longitude":127};
        latlong["KR"] = {"latitude":37, "longitude":127.5};
        latlong["KW"] = {"latitude":29.3375, "longitude":47.6581};
        latlong["KY"] = {"latitude":19.5, "longitude":-80.5};
        latlong["KZ"] = {"latitude":48, "longitude":68};
        latlong["LA"] = {"latitude":18, "longitude":105};
        latlong["LB"] = {"latitude":33.8333, "longitude":35.8333};
        latlong["LC"] = {"latitude":13.8833, "longitude":-61.1333};
        latlong["LI"] = {"latitude":47.1667, "longitude":9.5333};
        latlong["LK"] = {"latitude":7, "longitude":81};
        latlong["LR"] = {"latitude":6.5, "longitude":-9.5};
        latlong["LS"] = {"latitude":-29.5, "longitude":28.5};
        latlong["LT"] = {"latitude":55, "longitude":24};
        latlong["LU"] = {"latitude":49.75, "longitude":6};
        latlong["LV"] = {"latitude":57, "longitude":25};
        latlong["LY"] = {"latitude":25, "longitude":17};
        latlong["MA"] = {"latitude":32, "longitude":-5};
        latlong["MC"] = {"latitude":43.7333, "longitude":7.4};
        latlong["MD"] = {"latitude":47, "longitude":29};
        latlong["ME"] = {"latitude":42.5, "longitude":19.4};
        latlong["MG"] = {"latitude":-20, "longitude":47};
        latlong["MH"] = {"latitude":9, "longitude":168};
        latlong["MK"] = {"latitude":41.8333, "longitude":22};
        latlong["ML"] = {"latitude":17, "longitude":-4};
        latlong["MM"] = {"latitude":22, "longitude":98};
        latlong["MN"] = {"latitude":46, "longitude":105};
        latlong["MO"] = {"latitude":22.1667, "longitude":113.55};
        latlong["MP"] = {"latitude":15.2, "longitude":145.75};
        latlong["MQ"] = {"latitude":14.6667, "longitude":-61};
        latlong["MR"] = {"latitude":20, "longitude":-12};
        latlong["MS"] = {"latitude":16.75, "longitude":-62.2};
        latlong["MT"] = {"latitude":35.8333, "longitude":14.5833};
        latlong["MU"] = {"latitude":-20.2833, "longitude":57.55};
        latlong["MV"] = {"latitude":3.25, "longitude":73};
        latlong["MW"] = {"latitude":-13.5, "longitude":34};
        latlong["MX"] = {"latitude":23, "longitude":-102};
        latlong["MY"] = {"latitude":2.5, "longitude":112.5};
        latlong["MZ"] = {"latitude":-18.25, "longitude":35};
        latlong["NA"] = {"latitude":-22, "longitude":17};
        latlong["NC"] = {"latitude":-21.5, "longitude":165.5};
        latlong["NE"] = {"latitude":16, "longitude":8};
        latlong["NF"] = {"latitude":-29.0333, "longitude":167.95};
        latlong["NG"] = {"latitude":10, "longitude":8};
        latlong["NI"] = {"latitude":13, "longitude":-85};
        latlong["NL"] = {"latitude":52.5, "longitude":5.75};
        latlong["NO"] = {"latitude":62, "longitude":10};
        latlong["NP"] = {"latitude":28, "longitude":84};
        latlong["NR"] = {"latitude":-0.5333, "longitude":166.9167};
        latlong["NU"] = {"latitude":-19.0333, "longitude":-169.8667};
        latlong["NZ"] = {"latitude":-41, "longitude":174};
        latlong["OM"] = {"latitude":21, "longitude":57};
        latlong["PA"] = {"latitude":9, "longitude":-80};
        latlong["PE"] = {"latitude":-10, "longitude":-76};
        latlong["PF"] = {"latitude":-15, "longitude":-140};
        latlong["PG"] = {"latitude":-6, "longitude":147};
        latlong["PH"] = {"latitude":13, "longitude":122};
        latlong["PK"] = {"latitude":30, "longitude":70};
        latlong["PL"] = {"latitude":52, "longitude":20};
        latlong["PM"] = {"latitude":46.8333, "longitude":-56.3333};
        latlong["PR"] = {"latitude":18.25, "longitude":-66.5};
        latlong["PS"] = {"latitude":32, "longitude":35.25};
        latlong["PT"] = {"latitude":39.5, "longitude":-8};
        latlong["PW"] = {"latitude":7.5, "longitude":134.5};
        latlong["PY"] = {"latitude":-23, "longitude":-58};
        latlong["QA"] = {"latitude":25.5, "longitude":51.25};
        latlong["RE"] = {"latitude":-21.1, "longitude":55.6};
        latlong["RO"] = {"latitude":46, "longitude":25};
        latlong["RS"] = {"latitude":44, "longitude":21};
        latlong["RU"] = {"latitude":60, "longitude":100};
        latlong["RW"] = {"latitude":-2, "longitude":30};
        latlong["SA"] = {"latitude":25, "longitude":45};
        latlong["SB"] = {"latitude":-8, "longitude":159};
        latlong["SC"] = {"latitude":-4.5833, "longitude":55.6667};
        latlong["SD"] = {"latitude":15, "longitude":30};
        latlong["SE"] = {"latitude":62, "longitude":15};
        latlong["SG"] = {"latitude":1.3667, "longitude":103.8};
        latlong["SH"] = {"latitude":-15.9333, "longitude":-5.7};
        latlong["SI"] = {"latitude":46, "longitude":15};
        latlong["SJ"] = {"latitude":78, "longitude":20};
        latlong["SK"] = {"latitude":48.6667, "longitude":19.5};
        latlong["SL"] = {"latitude":8.5, "longitude":-11.5};
        latlong["SM"] = {"latitude":43.7667, "longitude":12.4167};
        latlong["SN"] = {"latitude":14, "longitude":-14};
        latlong["SO"] = {"latitude":10, "longitude":49};
        latlong["SR"] = {"latitude":4, "longitude":-56};
        latlong["ST"] = {"latitude":1, "longitude":7};
        latlong["SV"] = {"latitude":13.8333, "longitude":-88.9167};
        latlong["SY"] = {"latitude":35, "longitude":38};
        latlong["SZ"] = {"latitude":-26.5, "longitude":31.5};
        latlong["TC"] = {"latitude":21.75, "longitude":-71.5833};
        latlong["TD"] = {"latitude":15, "longitude":19};
        latlong["TF"] = {"latitude":-43, "longitude":67};
        latlong["TG"] = {"latitude":8, "longitude":1.1667};
        latlong["TH"] = {"latitude":15, "longitude":100};
        latlong["TJ"] = {"latitude":39, "longitude":71};
        latlong["TK"] = {"latitude":-9, "longitude":-172};
        latlong["TM"] = {"latitude":40, "longitude":60};
        latlong["TN"] = {"latitude":34, "longitude":9};
        latlong["TO"] = {"latitude":-20, "longitude":-175};
        latlong["TR"] = {"latitude":39, "longitude":35};
        latlong["TT"] = {"latitude":11, "longitude":-61};
        latlong["TV"] = {"latitude":-8, "longitude":178};
        latlong["TW"] = {"latitude":23.5, "longitude":121};
        latlong["TZ"] = {"latitude":-6, "longitude":35};
        latlong["UA"] = {"latitude":49, "longitude":32};
        latlong["UG"] = {"latitude":1, "longitude":32};
        latlong["UM"] = {"latitude":19.2833, "longitude":166.6};
        latlong["US"] = {"latitude":38, "longitude":-97};
        latlong["UY"] = {"latitude":-33, "longitude":-56};
        latlong["UZ"] = {"latitude":41, "longitude":64};
        latlong["VA"] = {"latitude":41.9, "longitude":12.45};
        latlong["VC"] = {"latitude":13.25, "longitude":-61.2};
        latlong["VE"] = {"latitude":8, "longitude":-66};
        latlong["VG"] = {"latitude":18.5, "longitude":-64.5};
        latlong["VI"] = {"latitude":18.3333, "longitude":-64.8333};
        latlong["VN"] = {"latitude":16, "longitude":106};
        latlong["VU"] = {"latitude":-16, "longitude":167};
        latlong["WF"] = {"latitude":-13.3, "longitude":-176.2};
        latlong["WS"] = {"latitude":-13.5833, "longitude":-172.3333};
        latlong["YE"] = {"latitude":15, "longitude":48};
        latlong["YT"] = {"latitude":-12.8333, "longitude":45.1667};
        latlong["ZA"] = {"latitude":-29, "longitude":24};
        latlong["ZM"] = {"latitude":-15, "longitude":30};
        latlong["ZW"] = {"latitude":-20, "longitude":30};
        
        latlong["GG"] = {"latitude":49.45, "longitude":2.55};
        latlong["IM"] = {"latitude":54.25, "longitude":4.5};
        latlong["JE"] = {"latitude":49.19, "longitude":2.11};
        latlong["PN"] = {"latitude":25.0667, "longitude":130.10};
        latlong["SS"] = {"latitude":4.85, "longitude":31.60};
        var mapData=[];
        $.ajax({
			type: "POST",
			url: "maprequest.jsp",
			async: false,
			data: {f1:st,f2:ed},
			success: function(data){
				 var x12 = data.trim();
				 var y12=x12.split("mnk");
				 var key=y12[0].split("mayankg");
				 var nam=y12[1].split("mayankp");
				 var val=y12[2].split("mayankt");
				 for (var i = 0; i < (key.length-1); i++) {
					var a1 = key[i];
					var b1 = nam[i];
					var c1 = val[i];
					mapData.push({
						"code": a1,
						"name": b1,
						"value": c1,
						"color":"#eea638"
					});
				 }
			}
			});
   
        var map;
        var minBulletSize =5;
        var maxBulletSize =70;
        var max=0;
        var min=0;
        if(mapData.length!=0)
        {
        	 var xx1=mapData.length-1;
             
              max = mapData[xx1].value;
              min = mapData[0].value;
        }
       
        
        // get min and max values
        /*for (var i = 0; i < mapData.length; i++) {
        	var value = 0;
        	var value1 = mapData[i].value;
        	if (value > value1) 
        		min = value;
        	else
        		max=value;
        }
*/
        // build map
        $(document).ready(function(){
          	AmCharts.theme = AmCharts.themes.dark;
        	map = new AmCharts.AmMap();
        	map.pathToImages = "http://seo.gyansha.com/SeoDeveloper/amcharts/images/";
        	map.addTitle("Live Demo Request", 14);
        	map.areasSettings = {
        		unlistedAreasColor: "#000000",
        		unlistedAreasAlpha: 0.1
        	};
        	map.imagesSettings.balloonText = "<span style='font-size:14px;'><b>[[title]]</b>: [[value]]</span>";

        	var dataProvider = {
        		mapVar: AmCharts.maps.worldLow,
        		images: []
        	}

        	// create circle for each country
        	for (var i = 0; i < mapData.length; i++) {
        		var dataItem = mapData[i];
        		var value = dataItem.value;
        		// calculate size of a bubble
        		var size = (value - min) / (max - min) * (maxBulletSize - minBulletSize) + minBulletSize;
        		if (size < minBulletSize) {
        			size = minBulletSize;
        		}
        		var id = dataItem.code;

        		dataProvider.images.push({
        			type: "circle",
        			width: size,
        			height: size,
        			color: dataItem.color,
        			longitude: latlong[id].longitude,
        			latitude: latlong[id].latitude,
        			title: dataItem.name,
        			value: value
        		});
        	}

        	map.dataProvider = dataProvider;
        	map.write("vmap_world");
        });
        $('#vmap_world').show(); 
    	$('#region_statistics_loading').hide();
         $('#region_statistics_content').show();
        },
        //This function is use to display chart For Site open in Desktop Or Mobile
        pieChart: function(str){
        	  $('#site_statistics_loading').show();
              $('#site_statistics_content').hide();
            var chartData1 = [];
			var chartData2 = [];
			var chartData3 = [];
			var chartData4 = [];
			var dd1=new Date();
			var dd=dd1.getDate();
			var mm=dd1.getMonth()+1;
			var yy=dd1.getFullYear();
			var date3=mm+"/"+dd+"/"+yy;
			var mob="",des="",non="";
       	 $.ajax({
				type: "POST",
				url: "visitrequestonly.jsp",
				async: true,
				data: {f1:str,f2:st,f3:ed},
				success: function(data){
						var x12 = data.trim();
						var y12=x12.split("mnk");
						mob=y12[0];
						des=y12[1];
						non=y12[2];	
						
						var str1=str+" Analysis";
			            if ($('#site_statistics').size() != 0) {

			                $('#site_statistics_loading').hide();
			                $('#site_statistics_content').show();

			                AmCharts.makeChart("site_statistics", {
			    			    "type": "pie",	
			    				"theme": "none",
			    			    "legend": {
			    			        "markerType": "circle",
			    			        //"position": "right",
			    					//"marginRight": 100,		
			    					"autoMargins": true
			    			    },
			    			    "dataProvider": [{
			    			        "Request": "Mobile",
			    			        "Number": mob
			    			    },{
			    			        "Request": "Desktop",
			    			        "Number": des
			    			    },{
			    			        "Request": "Not Known",
			    			        "Number": non
			    			    }],
			    			    "titles": [
			    			       		{
			    			       			"text": str1,
			    			       			"size": 14
			    			       		}
			    			       	],
			    			    
			    			    "valueField": "Number",
			    			    "titleField": "Request",
			    			    "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
			    			   /* "exportConfig": {
			    			        "menuTop":"0px",
			    			        "menuItems": [{
			    			            "icon": '../amcharts/images/export.png',
			    			            "format": 'png'
			    			        }]
			    			    }*/
			    			});
			            }
				}
				});
        },

        initCalendar: function () {
            if (!jQuery().fullCalendar) {
                return;
            }
            var mapTask=[];
            var cl=["yellow","red","purple","gray","green"];
            $('#calender_loading').show();
      	  $.ajax({
  			type: "POST",
  			url: "ServletAction",
  			async: false,
  			data: {
  				jEventName: "G_Task"
  				},
  				success: function(data){
      	    	 var x12 = data.trim();
      	    	 //alert(x12);
      	    	 var j=0;
				 var y12=x12.split("abcdef");
				 for (var i = 0; i < y12.length; i++) {
					var a1 = y12[i];
					var b1 = a1.split("abczxy");
						//alert(b1[0]);
						//alert(b1[1]);
					if(j>4)
						j=0;
					
					mapTask.push({
						title: b1[0],
						start: b1[1],
						end:b1[2],
						backgroundColor: Metronic.getBrandColor(cl[j]),
	                    allDay: false
					});
					j++;
				 }
      	    	
      	    }
      	  });
      	 $('#calender_loading').hide();
            var date = new Date();
            var d = date.getDate();
            var m = date.getMonth();
            var y = date.getFullYear();

            var h = {};

            if ($('#calendar').width() <= 400) {
                $('#calendar').addClass("mobile");
                h = {
                    left: 'title, prev, next',
                    center: '',
                    right: 'today,month,agendaWeek,agendaDay'
                };
            } else {
                $('#calendar').removeClass("mobile");
                if (Metronic.isRTL()) {
                    h = {
                        right: 'title',
                        center: '',
                        left: 'prev,next,today,month,agendaWeek,agendaDay'
                    };
                } else {
                    h = {
                        left: 'title',
                        center: '',
                        right: 'prev,next,today,month,agendaWeek,agendaDay'
                    };
                }
            }

            $('#calendar').fullCalendar('destroy'); // destroy the calendar
            $('#calendar').fullCalendar({ //re-initialize the calendar
                disableDragging: false,
                header: h,
                editable: false,
                events: mapTask
            });
        },
        
      //This function is use to display Day wise Chart
        dayCharts: function(){
        	  $('#day_chart_loading').show();
              $('#day_chart_content').hide();
			 var hadoop=[];
			 var mongodb=[];
			 var python=[];
       	 $.ajax({
				type: "POST",
				url: "vrequest.jsp",
				async: true,
				data: {f1:st,f2:ed,f3:"day"},
				success: function(data){
						var x12 = data.trim();
						var y12=x12.split("mnk");
						hadoop=y12[0].split("hadoop");
						mongodb=y12[1].split("mongodb");
						python=y12[2].split("python");
						
						
						 if ($('#day_chart').size() != 0) {
				                $('#day_chart_loading').hide();
				                $('#day_chart_content').show();

				                AmCharts.makeChart("day_chart", {
				                    "type": "serial",
				                	"theme": "light",
				                    "pathToImages": "http://seo.gyansha.com/SeoDeveloper/amcharts/images/",
				                	"autoMargins": true,
				                	/*"marginLeft":50,
				                	"marginRight":8,
				                	"marginTop":10,*/
				                	"legend": {
				                        "useGraphSettings": true,
				                        "markerSize":12,
				                        "valueWidth":0,
				                        "verticalGap":0
				                    },
				                	"marginBottom":26,
				                	"dataProvider": [{
				                        "Days": "Sunday",
				                        "Total": parseInt(hadoop[0])+parseInt(python[0])+parseInt(mongodb[0]),
				                        "Hadoop": hadoop[0],
				                        "Python": python[0],
				                        "MongoDB": mongodb[0],
				                    },
				                    {
				                        "Days": "Monday",
				                        "Total": parseInt(hadoop[1])+parseInt(python[1])+parseInt(mongodb[1]),
				                        "Hadoop": hadoop[1],
				                        "Python": python[1],
				                        "MongoDB": mongodb[1],
				                    },{
				                        "Days": "Tuesday",
				                        "Total": parseInt(hadoop[2])+parseInt(python[2])+parseInt(mongodb[2]),
				                        "Hadoop": hadoop[2],
				                        "Python": python[2],
				                        "MongoDB": mongodb[2],
				                    },{
				                        "Days": "Wednesday",
				                        "Total": parseInt(hadoop[3])+parseInt(python[3])+parseInt(mongodb[3]),
				                        "Hadoop": hadoop[3],
				                        "Python": python[3],
				                        "MongoDB": mongodb[3],
				                    },{
				                        "Days": "Thursday",
				                        "Total": parseInt(hadoop[4])+parseInt(python[4])+parseInt(mongodb[4]),
				                        "Hadoop": hadoop[4],
				                        "Python": python[4],
				                        "MongoDB": mongodb[4],
				                    },{
				                        "Days": "Friday",
				                        "Total": parseInt(hadoop[5])+parseInt(python[5])+parseInt(mongodb[5]),
				                        "Hadoop": hadoop[5],
				                        "Python": python[5],
				                        "MongoDB": mongodb[5],
				                    },{
				                        "Days": "Saturday",
				                        "Total": parseInt(hadoop[6])+parseInt(python[6])+parseInt(mongodb[6]),
				                        "Hadoop": hadoop[6],
				                        "Python": python[6],
				                        "MongoDB": mongodb[6],
				                    }],
				                    "valueAxes": [{
				                        "axisAlpha": 0,
				                        "position": "left",
				                        "title": "Live Demo Request"
				                    }],
				                    "startDuration": 1,
				                    "graphs": [{
				                        "alphaField": "alpha",
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "dashLengthField": "dashLengthColumn",
				                        "fillAlphas": 1,
				                        "title": "Total",
				                        "type": "column",
				                        "valueField": "Total"
				                    }, {
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "bullet": "round",
				                        "dashLengthField": "dashLengthLine",
				                        "lineThickness": 3,
				                		"bulletSize": 7,
				                		"bulletBorderAlpha": 1,
				                		"bulletColor": "#FFFFFF",
				                		"useLineColorForBulletBorder": true,
				                		"bulletBorderThickness": 3,
				                		"fillAlphas": 0,
				                		"lineAlpha": 1,
				                        "title": "Hadoop",
				                        "valueField": "Hadoop"
				                    },
				                    {
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "bullet": "round",
				                        "dashLengthField": "dashLengthLine",
				                        "lineThickness": 3,
				                		"bulletSize": 7,
				                		"bulletBorderAlpha": 1,
				                		"bulletColor": "#FFFFFF",
				                		"useLineColorForBulletBorder": true,
				                		"bulletBorderThickness": 3,
				                		"fillAlphas": 0,
				                		"lineAlpha": 1,
				                        "title": "MongoDB",
				                        "valueField": "MongoDB"
				                    },
				                    {
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "bullet": "round",
				                        "dashLengthField": "dashLengthLine",
				                        "lineThickness": 3,
				                		"bulletSize": 7,
				                		"bulletBorderAlpha": 1,
				                		"bulletColor": "#FFFFFF",
				                		"useLineColorForBulletBorder": true,
				                		"bulletBorderThickness": 3,
				                		"fillAlphas": 0,
				                		"lineAlpha": 1,
				                        "title": "Python",
				                        "valueField": "Python"
				                    }],
				                    "categoryField": "Days",
				                    "categoryAxis": {
				                        "gridPosition": "start",
				                		"axisAlpha":0,
				                		"tickLength":0
				                    }
				                });
				            }
					}
				});
        },
        
        //This function is use to display Time wise Chart
        timeCharts: function(){
        	  $('#time_chart_loading').show();
              $('#time_chart_content').hide();
			 var hadoop=[];
			 var mongodb=[];
			 var python=[];
       	 $.ajax({
				type: "POST",
				url: "vrequest.jsp",
				async: true,
				data: {f1:st,f2:ed,f3:"time"},
				success: function(data){
						var x12 = data.trim();
						var y12=x12.split("mnk");
						hadoop=y12[0].split("hadoop");
						mongodb=y12[1].split("mongodb");
						python=y12[2].split("python");
						
						 if ($('#time_chart').size() != 0) {
				                $('#time_chart_loading').hide();
				                $('#time_chart_content').show();

				                AmCharts.makeChart("time_chart", {
				                    "type": "serial",
				                	"theme": "light",
				                    "pathToImages": "http://seo.gyansha.com/SeoDeveloper/amcharts/images/",
				                	"autoMargins": true,
				                	/*"marginLeft":50,
				                	"marginRight":8,
				                	"marginTop":10,*/
				                	"legend": {
				                        "useGraphSettings": true,
				                        "markerSize":12,
				                        "valueWidth":0,
				                        "verticalGap":0
				                    },
				                	"marginBottom":26,
				                	"dataProvider": [{
				                        "Time": "6:00 PM - 6:59 PM",
				                        "Total": parseInt(hadoop[18])+parseInt(python[18])+parseInt(mongodb[18]),
				                        "Hadoop": hadoop[18],
				                        "Python": python[18],
				                        "MongoDB": mongodb[18],
				                    },{
				                        "Time": "7:00 PM - 7:59 PM",
				                        "Total": parseInt(hadoop[19])+parseInt(python[19])+parseInt(mongodb[19]),
				                        "Hadoop": hadoop[19],
				                        "Python": python[19],
				                        "MongoDB": mongodb[19],
				                    },{
				                        "Time": "8:00 PM - 8:59 PM",
				                        "Total": parseInt(hadoop[20])+parseInt(python[20])+parseInt(mongodb[20]),
				                        "Hadoop": hadoop[20],
				                        "Python": python[20],
				                        "MongoDB": mongodb[20],
				                    },{
				                        "Time": "9:00 PM - 9:59 PM",
				                        "Total": parseInt(hadoop[21])+parseInt(python[21])+parseInt(mongodb[21]),
				                        "Hadoop": hadoop[21],
				                        "Python": python[21],
				                        "MongoDB": mongodb[21],
				                    },{
				                        "Time": "10:00 PM - 10:59 PM",
				                        "Total": parseInt(hadoop[22])+parseInt(python[22])+parseInt(mongodb[22]),
				                        "Hadoop": hadoop[22],
				                        "Python": python[22],
				                        "MongoDB": mongodb[22],
				                    },{
				                        "Time": "11:00 PM - 11:59 PM",
				                        "Total": parseInt(hadoop[23])+parseInt(python[23])+parseInt(mongodb[23]),
				                        "Hadoop": hadoop[23],
				                        "Python": python[23],
				                        "MongoDB": mongodb[23],
				                    },
				                    {
				                        "Time": "12:00 AM - 12:59 AM",
				                        "Total": parseInt(hadoop[0])+parseInt(python[0])+parseInt(mongodb[0]),
				                        "Hadoop": hadoop[0],
				                        "Python": python[0],
				                        "MongoDB": mongodb[0],
				                    },{
				                        "Time": "1:00 AM - 1:59 AM",
				                        "Total": parseInt(hadoop[1])+parseInt(python[1])+parseInt(mongodb[1]),
				                        "Hadoop": hadoop[1],
				                        "Python": python[1],
				                        "MongoDB": mongodb[1],
				                    },{
				                        "Time": "2:00 AM - 2:59 AM",
				                        "Total": parseInt(hadoop[2])+parseInt(python[2])+parseInt(mongodb[2]),
				                        "Hadoop": hadoop[2],
				                        "Python": python[2],
				                        "MongoDB": mongodb[2],
				                    },{
				                        "Time": "3:00 AM - 3:59 AM",
				                        "Total": parseInt(hadoop[3])+parseInt(python[3])+parseInt(mongodb[3]),
				                        "Hadoop": hadoop[3],
				                        "Python": python[3],
				                        "MongoDB": mongodb[3],
				                    },{
				                        "Time": "4:00 AM - 4:59 AM",
				                        "Total": parseInt(hadoop[4])+parseInt(python[4])+parseInt(mongodb[4]),
				                        "Hadoop": hadoop[4],
				                        "Python": python[4],
				                        "MongoDB": mongodb[4],
				                    },{
				                        "Time": "5:00 AM - 5:59 AM",
				                        "Total": parseInt(hadoop[5])+parseInt(python[5])+parseInt(mongodb[5]),
				                        "Hadoop": hadoop[5],
				                        "Python": python[5],
				                        "MongoDB": mongodb[5],
				                    },{
				                        "Time": "6:00 AM - 6:59 AM",
				                        "Total": parseInt(hadoop[6])+parseInt(python[6])+parseInt(mongodb[6]),
				                        "Hadoop": hadoop[6],
				                        "Python": python[6],
				                        "MongoDB": mongodb[6],
				                    },{
				                        "Time": "7:00 AM - 7:59 AM",
				                        "Total": parseInt(hadoop[7])+parseInt(python[7])+parseInt(mongodb[7]),
				                        "Hadoop": hadoop[7],
				                        "Python": python[7],
				                        "MongoDB": mongodb[7],
				                    },{
				                        "Time": "8:00 AM - 8:59 AM",
				                        "Total": parseInt(hadoop[8])+parseInt(python[8])+parseInt(mongodb[8]),
				                        "Hadoop": hadoop[8],
				                        "Python": python[8],
				                        "MongoDB": mongodb[8],
				                    },{
				                        "Time": "9:00 AM - 9:59 AM",
				                        "Total": parseInt(hadoop[9])+parseInt(python[9])+parseInt(mongodb[9]),
				                        "Hadoop": hadoop[9],
				                        "Python": python[9],
				                        "MongoDB": mongodb[9],
				                    },{
				                        "Time": "10:00 AM - 10:59 AM",
				                        "Total": parseInt(hadoop[10])+parseInt(python[10])+parseInt(mongodb[10]),
				                        "Hadoop": hadoop[10],
				                        "Python": python[10],
				                        "MongoDB": mongodb[10],
				                    },{
				                        "Time": "11:00 AM - 11:59 AM",
				                        "Total": parseInt(hadoop[11])+parseInt(python[11])+parseInt(mongodb[11]),
				                        "Hadoop": hadoop[11],
				                        "Python": python[11],
				                        "MongoDB": mongodb[11],
				                    },{
				                        "Time": "12:00 PM - 12:59 PM",
				                        "Total": parseInt(hadoop[12])+parseInt(python[12])+parseInt(mongodb[12]),
				                        "Hadoop": hadoop[12],
				                        "Python": python[12],
				                        "MongoDB": mongodb[12],
				                    },{
				                        "Time": "1:00 PM - 1:59 PM",
				                        "Total": parseInt(hadoop[13])+parseInt(python[13])+parseInt(mongodb[13]),
				                        "Hadoop": hadoop[13],
				                        "Python": python[13],
				                        "MongoDB": mongodb[13],
				                    },{
				                        "Time": "2:00 PM - 2:59 PM",
				                        "Total": parseInt(hadoop[14])+parseInt(python[14])+parseInt(mongodb[14]),
				                        "Hadoop": hadoop[14],
				                        "Python": python[14],
				                        "MongoDB": mongodb[14],
				                    },{
				                        "Time": "3:00 PM - 3:59 PM",
				                        "Total": parseInt(hadoop[15])+parseInt(python[15])+parseInt(mongodb[15]),
				                        "Hadoop": hadoop[15],
				                        "Python": python[15],
				                        "MongoDB": mongodb[15],
				                    },{
				                        "Time": "4:00 PM - 4:59 PM",
				                        "Total": parseInt(hadoop[16])+parseInt(python[16])+parseInt(mongodb[16]),
				                        "Hadoop": hadoop[16],
				                        "Python": python[16],
				                        "MongoDB": mongodb[16],
				                    },{
				                        "Time": "5:00 PM - 5:59 PM",
				                        "Total": parseInt(hadoop[17])+parseInt(python[17])+parseInt(mongodb[17]),
				                        "Hadoop": hadoop[17],
				                        "Python": python[17],
				                        "MongoDB": mongodb[17],
				                    }
				                    
				                    
				                    ],
				                    "valueAxes": [{
				                        "axisAlpha": 0,
				                        "position": "left",
				                        	"title": "Live Demo Request"
				                    }],
				                    "startDuration": 1,
				                    "graphs": [{
				                        "alphaField": "alpha",
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "dashLengthField": "dashLengthColumn",
				                        "fillAlphas": 1,
				                        "title": "Total",
				                        "type": "column",
				                        "valueField": "Total"
				                    }, {
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "bullet": "round",
				                        "dashLengthField": "dashLengthLine",
				                        "lineThickness": 3,
				                		"bulletSize": 7,
				                		"bulletBorderAlpha": 1,
				                		"bulletColor": "#FFFFFF",
				                		"useLineColorForBulletBorder": true,
				                		"bulletBorderThickness": 3,
				                		"fillAlphas": 0,
				                		"lineAlpha": 1,
				                        "title": "Hadoop",
				                        "valueField": "Hadoop"
				                    },
				                    {
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "bullet": "round",
				                        "dashLengthField": "dashLengthLine",
				                        "lineThickness": 3,
				                		"bulletSize": 7,
				                		"bulletBorderAlpha": 1,
				                		"bulletColor": "#FFFFFF",
				                		"useLineColorForBulletBorder": true,
				                		"bulletBorderThickness": 3,
				                		"fillAlphas": 0,
				                		"lineAlpha": 1,
				                        "title": "MongoDB",
				                        "valueField": "MongoDB"
				                    },
				                    {
				                        "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
				                        "bullet": "round",
				                        "dashLengthField": "dashLengthLine",
				                        "lineThickness": 3,
				                		"bulletSize": 7,
				                		"bulletBorderAlpha": 1,
				                		"bulletColor": "#FFFFFF",
				                		"useLineColorForBulletBorder": true,
				                		"bulletBorderThickness": 3,
				                		"fillAlphas": 0,
				                		"lineAlpha": 1,
				                        "title": "Python",
				                        "valueField": "Python"
				                    }],
				                    "categoryField": "Time",
				                    "categoryAxis": {
				                        "gridPosition": "start",
				                		"axisAlpha":0,
				                		"tickLength":0,
				                		"labelRotation": 90
				                		
				                    }
				                });
				            }
					}
				});
        },        
        
        initCharts: function (str) {
            
            $('#site_activities_loading').show();
            $('#site_activities_content').hide();
       	 	var str1=str+" Analysis";
                var chartData1=[];
                $.ajax({
					type: "POST",
					url: "request.jsp",
					async: false,
					data: { f1:st,f2:ed,f3:str},
					success: function(data){
							var x12 = data.trim();
							var y12=x12.split("mnk");
							var x1=y12[0].split("mayankg");
							var x2=y12[1].split("mayanku");
							var x3=y12[2].split("mayankp");
							var x4=y12[3].split("mayankt");
							var x5=y12[4].split("mayanka");
							for (var i = 0; i < (x1.length-1); i++) {
									var a1 = parseInt(x2[i]);
									var a2 = parseInt(x3[i]);
									var a3 = parseInt(x4[i]);
									var a4 = parseInt(x5[i]);
									var a5=a1+a2+a3+a4;
									var b4 = x1[i];
									chartData1.push({
										"date": b4,
										"total": a5,
										"main": a4,
										"facebook":a1,
										"banner":a2,
										"text":a3
									});
							}
							
							
							  $('#site_activities_loading').hide();
				                $('#site_activities_content').show();
				              
				             AmCharts.makeChart("site_activities", {
				                    "type": "serial",
				                    "theme": "none",
				                	"autoMargins": true,
				                    "legend": {
				                        "equalWidths": false,
				                        "useGraphSettings": true,
				                        "valueAlign": "left",
				                        "valueWidth": 120
				                    },
				                    "titles": [
				       			       		{
				       			       			"text": str1,
				       			       			"size": 14
				       			       		}
				       			       	],
				       			    
				                    "dataProvider": chartData1,
				                    "valueAxes": [{
				                        "id": "requestAxis",
				                        "axisAlpha": 0,
				                        "gridAlpha": 0,
				                        "position": "left",
				                        "title": "Live Demo Request"
				                    }],
				                    "graphs": [{
				                        "alphaField": "alpha",
				                        "balloonText": "[[value]]",
				                        "dashLengthField": "dashLength",
				                        "fillAlphas": 0.7,
				                        "legendPeriodValueText": "total: [[value.sum]]",
				                        "legendValueText": "[[value]]",
				                        "title": "Total",
				                        "type": "column",
				                        "valueField": "total",
				                        "valueAxis": "requestAxis"
				                    }, {
				                        "balloonText": "latitude:[[value]]",
				                        "bullet": "round",
				                        "bulletBorderAlpha": 1,
				                        "useLineColorForBulletBorder": true,
				                        "bulletColor": "#FFFFFF",
				                        "bulletSizeField": "townSize",
				                        "dashLengthField": "dashLength",
				                        //"descriptionField": "townName",
				                        "labelPosition": "right",
				                       // "labelText": "[[townName2]]",
				                        "legendValueText": "[[value]]",
				                        "title": "Main",
				                        "fillAlphas": 0,
				                        "valueField": "main",
				                        "valueAxis": "requestAxis"
				                    }, {
				                        "bullet": "square",
				                        "bulletBorderAlpha": 1,
				                        "bulletBorderThickness": 1,
				                        "dashLengthField": "dashLength",
				                        "legendValueText": "[[value]]",
				                        "title": "Facebook",
				                        "fillAlphas": 0,
				                        "valueField": "facebook",
				                        "valueAxis": "requestAxis"
				                    },{
				                        "bullet": "square",
				                        "bulletBorderAlpha": 1,
				                        "bulletBorderThickness": 1,
				                        "dashLengthField": "dashLength",
				                        "legendValueText": "[[value]]",
				                        "title": "Banner Ads.",
				                        "fillAlphas": 0,
				                        "valueField": "banner",
				                        "valueAxis": "requestAxis"
				                    },{
				                        "bullet": "square",
				                        "bulletBorderAlpha": 1,
				                        "bulletBorderThickness": 1,
				                        "dashLengthField": "dashLength",
				                        "legendValueText": "[[value]]",
				                        "title": "Text Ads.",
				                        "fillAlphas": 0,
				                        "valueField": "text",
				                        "valueAxis": "requestAxis"
				                    }],
				                    "chartCursor": {
				                        "categoryBalloonDateFormat": "DD",
				                        "cursorAlpha": 0.1,
				                        "cursorColor":"#000000",
				                         "fullWidth":true,
				                        "valueBalloonsEnabled": false,
				                        "zoomable": false
				                    },
				                    "dataDateFormat": "YYYY-MM-DD",
				                    "categoryField": "date",
				                    "categoryAxis": {
				                        "dateFormats": [{
				                            "period": "DD",
				                            "format": "DD"
				                        }, {
				                            "period": "WW",
				                            "format": "MMM DD"
				                        }, {
				                            "period": "MM",
				                            "format": "MMM"
				                        }, {
				                            "period": "YYYY",
				                            "format": "YYYY"
				                        }],
				                        "parseDates": true,
				                        "autoGridCount": false,
				                        "axisColor": "#555555",
				                        "gridAlpha": 0.1,
				                        "gridColor": "#FFFFFF",
				                        "gridCount": 50
				                    },
				                    "exportConfig": {
				                        "menuBottom": "20px",
				                        "menuRight": "22px",
				                        "menuItems": [{
				                            "icon": 'http://seo.gyansha.com/SeoDeveloper/amcharts/images/export.png',
				                            "format": 'png'
				                        }]
				                    }
				                });
						}
					});
        },

        initMiniCharts: function () {

            // IE8 Fix: function.bind polyfill
            if (Metronic.isIE8() && !Function.prototype.bind) {
                Function.prototype.bind = function (oThis) {
                    if (typeof this !== "function") {
                        // closest thing possible to the ECMAScript 5 internal IsCallable function
                        throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
                    }

                    var aArgs = Array.prototype.slice.call(arguments, 1),
                        fToBind = this,
                        fNOP = function () {},
                        fBound = function () {
                            return fToBind.apply(this instanceof fNOP && oThis ? this : oThis,
                        aArgs.concat(Array.prototype.slice.call(arguments)));
                    };

                    fNOP.prototype = this.prototype;
                    fBound.prototype = new fNOP();

                    return fBound;
                };
            }

            $('.easy-pie-chart .number.transactions').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: Metronic.getBrandColor('yellow')
            });

            $('.easy-pie-chart .number.visits').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: Metronic.getBrandColor('green')
            });

            $('.easy-pie-chart .number.bounce').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: Metronic.getBrandColor('red')
            });

            $('.easy-pie-chart-reload').click(function () {
                $('.easy-pie-chart .number').each(function () {
                    var newValue = Math.floor(100 * Math.random());
                    $(this).data('easyPieChart').update(newValue);
                    $('span', this).text(newValue);
                });
            });

            $("#sparkline_bar").sparkline([8, 9, 10, 11, 10, 10, 12, 10, 10, 11, 9, 12, 11, 10, 9, 11, 13, 13, 12], {
                type: 'bar',
                width: '100',
                barWidth: 5,
                height: '55',
                barColor: '#35aa47',
                negBarColor: '#e02222'
            });

            $("#sparkline_bar2").sparkline([9, 11, 12, 13, 12, 13, 10, 14, 13, 11, 11, 12, 11, 11, 10, 12, 11, 10], {
                type: 'bar',
                width: '100',
                barWidth: 5,
                height: '55',
                barColor: '#ffb848',
                negBarColor: '#e02222'
            });

            $("#sparkline_line").sparkline([9, 10, 9, 10, 10, 11, 12, 10, 10, 11, 11, 12, 11, 10, 12, 11, 10, 12], {
                type: 'line',
                width: '100',
                height: '55',
                lineColor: '#ffb848'
            });

        },

        initChat: function () {

            var cont = $('#chats');
            var list = $('.chats', cont);
            var form = $('.chat-form', cont);
            var input = $('input', form);
            var btn = $('.btn', form);

            var handleClick = function (e) {
                e.preventDefault();

                var text = input.val();
                if (text.length == 0) {
                    return;
                }

                var time = new Date();
                var time_str = (time.getHours() + ':' + time.getMinutes());
                var tpl = '';
                tpl += '<li class="out">';
                tpl += '<img class="avatar" alt="" src="' + Layout.getLayoutImgPath() + 'avatar1.jpg"/>';
                tpl += '<div class="message">';
                tpl += '<span class="arrow"></span>';
                tpl += '<a href="#" class="name">Bob Nilson</a>&nbsp;';
                tpl += '<span class="datetime">at ' + time_str + '</span>';
                tpl += '<span class="body">';
                tpl += text;
                tpl += '</span>';
                tpl += '</div>';
                tpl += '</li>';

                var msg = list.append(tpl);
                input.val("");

                var getLastPostPos = function () {
                    var height = 0;
                    cont.find("li.out, li.in").each(function () {
                        height = height + $(this).outerHeight();
                    });

                    return height;
                }

                cont.find('.scroller').slimScroll({
                    scrollTo: getLastPostPos()
                });
            }

            $('body').on('click', '.message .name', function (e) {
                e.preventDefault(); // prevent click event

                var name = $(this).text(); // get clicked user's full name
                input.val('@' + name + ':'); // set it into the input field
                Metronic.scrollTo(input); // scroll to input if needed
            });

            btn.click(handleClick);

            input.keypress(function (e) {
                if (e.which == 13) {
                    handleClick(e);
                    return false; //<---- Add this line
                }
            });
        },

        initDashboardDaterange: function () {
        	 var getRequests = function (d,td) {
        		
        		 $('#hadoop_loading').show();
        		 $('#python_loading').show();
        		 $('#mongo_loading').show();
        		 $('#r_loading').show();
        		 $('#scala_loading').show();
        		 $('#total_loading').show();
        		 $.get("livedemo.do",
        	        	    {
        	        	    		d: d,
        	             	      td: td,
        	             	      jEventName: "Requests"
        	        	    },
        	        	    function(data,status){
        	        	      //alert("Data: " + data + "\nStatus: " + status);
        	        	    	//alert(data);
        	        	    	var arr=data.split("/");
        	        	    	 $('#hadoop_loading').hide();
        	        	    	 $('#python_loading').hide();
        	            		 $('#mongo_loading').hide();
        	            		 $('#r_loading').hide();
        	            		 $('#scala_loading').hide();
        	            		 $('#total_loading').hide();
        	                     $('#hadoop').show();
        	        	    	$('#hadoop').html(arr[0]);
        	        	    	$('#python').show();
        	        	    	$('#python').html(arr[1]);
        	        	    	  $('#mongo').show();
          	        	    	$('#mongo').html(arr[2]);
          	        	    	 $('#r').show();
           	        	    	$('#r').html(arr[3]);
           	        	    	$('#scala').show();
        	        	    	$('#scala').html(arr[4]);
          	        	    	$('#total').show();
        	        	    	$('#total').html(arr[5]);
        	        	    	$("#hadoopurl").attr("href", "livedemo.do?jEventName=V_L_Course&course=Hadoop&d="+d+"&td="+td);
        	        	    	$("#pythonurl").attr("href", "livedemo.do?jEventName=V_L_Course&course=Python&d="+d+"&td="+td);
        	        	    	$("#mongourl").attr("href", "livedemo.do?jEventName=V_L_Course&course=MongoDB&d="+d+"&td="+td);
        	        	    	$("#rurl").attr("href", "livedemo.do?jEventName=V_L_Course&course=Business Analytics With R&d="+d+"&td="+td);
        	        	    	$("#scalaurl").attr("href", "livedemo.do?jEventName=V_L_Course&course=Apache Spark and Scala&d="+d+"&td="+td);
        	        	    	$("#allurl").attr("href", "livedemo.do?jEventName=V_L_Course&course=All&d="+d+"&td="+td);
          	        	    	
        	        	    });
        		 
        	 }
        	 var getNewRequests = function (d,td) {
         		//alert('hello');
        		 $('#hadoopnew_loading').show();
        		 $('#pythonnew_loading').show();
        		 $('#mongonew_loading').show();
        		 $('#rnew_loading').show();
        		 $('#scalanew_loading').show();
        		 $('#totalnew_loading').show();
        		 $.get("livedemo.do",
        	        	    {
        	        	    		d: d,
        	             	      td: td,
        	             	      jEventName: "NewRequests"
        	        	    },
        	        	    function(data,status){
        	        	      //alert("Data: " + data + "\nStatus: " + status);
        	        	    	//alert(data);
        	        	    	
        	        	    	var arr=data.split("/");
        	        	    	 $('#hadoopnew_loading').hide();
        	        	    	 $('#pythonnew_loading').hide();
        	            		 $('#mongonew_loading').hide();
        	            		 $('#rnew_loading').hide();
        	            		 $('#scalanew_loading').hide();
        	            		 $('#totalnew_loading').hide();
        	                     $('#hadoopnew').show();
        	                     //alert("m"+arr[3].trim()+"m");
        	                     if(arr[0]==="0")
        	                    	 {
        	                    	 
        	                    	 if($('#hadoopstat').hasClass('dashboard-stat red'))
        	                    		 {
        	                    		 	$('#hadoopstat').removeClass('red');
        	                    		 	$('#hadoopstat').addClass('grey');
        	                    		 	
        	                    		 }
        	                    	
        	                    	 }
        	                     else
        	                    	 {
        	                    	 if($('#hadoopstat').hasClass('dashboard-stat grey'))
    	                    		 {
        	                    		 $('#hadoopstat').removeClass('grey');
     	                    		 	$('#hadoopstat').addClass('red');
    	                    		 }
        	                    	 }
        	        	    	$('#hadoopnew').html(arr[0]);
        	        	    	$('#pythonnew').show();
        	        	    	 if(arr[1]==="0")
        	        	    		 {
        	        	    		 
        	        	    		 if($('#pythonstat').hasClass('dashboard-stat red'))
    	                    		 {
    	                    		 	$('#pythonstat').removeClass('red');
    	                    		 	$('#pythonstat').addClass('grey');
    	                    		 	
    	                    		 }
        	        	    		
        	        	    		 }
        	                     else
        	                    	 {
        	                    	 if($('#pythonstat').hasClass('dashboard-stat grey'))
    	                    		 {
        	                    		 $('#pythonstat').removeClass('grey');
        	        	    			  $('#pythonstat').addClass('red'); 
    	                    		 }
        	                    	 }
        	        	    	$('#pythonnew').html(arr[1]);
        	        	    	  $('#mongonew').show();
        	        	    	  if(arr[2]==="0")
        	        	    		  {
        	        	    		  if($('#mongostat').hasClass('dashboard-stat red'))
     	                    		 	{
     	                    		 	$('#mongostat').removeClass('red');
     	                    		 	$('#mongostat').addClass('grey');
     	                    		 	
     	                    		 	}
        	        	    		  
        	        	    		  } 
         	                     else
         	                    	 {
         	                    	if($('#mongostat').hasClass('dashboard-stat grey'))
 	                    		 	{
         	                    		 $('#mongostat').removeClass('grey');
       	        	    			     $('#mongostat').addClass('red');
 	                    		 	}
         	                    	 }
        	        	    	  $('#mongonew').html(arr[2]);
        	        	    	  $('#rnew').show();
        	        	    	  if(arr[3]==="0")
        	        	    		  {
        	        	    		  if($('#rstat').hasClass('dashboard-stat red'))
     	                    		 	{
     	                    		 	$('#rstat').removeClass('red');
     	                    		 	$('#rstat').addClass('grey');
     	                    		 	
     	                    		 	}
        	        	    		  
        	        	    		  } 
         	                     else
         	                    	 {
         	                    	if($('#rstat').hasClass('dashboard-stat grey'))
 	                    		 	{
         	                    		 $('#rstat').removeClass('grey');
       	        	    			     $('#rstat').addClass('red');
 	                    		 	}
         	                    	 }
        	        	    	  $('#rnew').html(arr[3]);
        	        	    	  $('#scalanew').show();
        	        	    	  if(arr[4]==="0")
        	        	    		  {
        	        	    		  if($('#scalastat').hasClass('dashboard-stat red'))
     	                    		 	{
     	                    		 	$('#scalastat').removeClass('red');
     	                    		 	$('#scalastat').addClass('grey');
     	                    		 	
     	                    		 	}
        	        	    		  
        	        	    		  } 
         	                     else
         	                    	 {
         	                    	if($('#scalastat').hasClass('dashboard-stat grey'))
 	                    		 	{
         	                    		 $('#scalastat').removeClass('grey');
       	        	    			     $('#scalastat').addClass('red');
 	                    		 	}
         	                    	 }
        	        	    	  $('#scalanew').html(arr[4]);
        	        	    	  $('#totalnew').show();
        	        	    	  if(arr[5].trim()==="0")
        	        	    		  {
        	        	    		 
        	        	    		  if($('#totalstat').hasClass('dashboard-stat red'))
     	                    		 	{
        	        	    			  
     	                    		 	$('#totalstat').removeClass('red');
     	                    		 	$('#totalstat').addClass('grey');
     	                    		 	
     	                    		 	}
        	        	    		 
        	        	    		 } 
         	                     else
         	                    	 {
         	                    	if($('#totalstat').hasClass('dashboard-stat grey'))
 	                    		 	{
         	                    		$('#totalstat').removeClass('grey');
    	        	    		  		$('#totalstat').addClass('red');
 	                    		 	}
         	                    	
         	                    	 }
        	        	    	  $('#totalnew').html(arr[5]);
          	        	    	
        	        	    });
        		 
        	 }
        	 
     
            $('#dashboard-report-range').daterangepicker({
                    opens: (Metronic.isRTL() ? 'right' : 'left'),
                    startDate: moment(),//.subtract('days', 29),
                    endDate: moment(),
                    minDate: '01/01/2014',
                    maxDate: '12/31/2016',
                    dateLimit: {
                        days: 365
                    },
                    showDropdowns: false,
                    showWeekNumbers: true,
                    timePicker: false,
                    timePickerIncrement: 1,
                    timePicker12Hour: true,
                    ranges: {
                    	'Tomorrow': [moment().add('days', 1), moment().add('days', 1)],
                        'Today': [moment(), moment()],
                        'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                        'Last 7 Days': [moment().subtract('days', 6), moment()],
                        'Last 30 Days': [moment().subtract('days', 29), moment()],
                        'This Month': [moment().startOf('month'), moment().endOf('month')],
                        'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')],
                        'All Time': ['07/01/2014', moment()]
                    },
                    buttonClasses: ['btn btn-sm'],
                    applyClass: ' blue',
                    cancelClass: 'default',
                    format: 'MM/DD/YYYY',
                    separator: ' to ',
                    locale: {
                        applyLabel: 'Apply',
                        fromLabel: 'From',
                        toLabel: 'To',
                        customRangeLabel: 'Custom Range',
                        daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
                        monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                        firstDay: 1
                    }
                },
                function (start, end) {
                    $('#dashboard-report-range span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                    //alert(start.format('MMMM D, YYYY'));
                    var d=start.format('MMMM D, YYYY');
                    var td=end.format('MMMM D, YYYY');
                    //alert(td);
                    getRequests(d,td);
                    getNewRequests(d,td);
                   
                    //st=start.format('MM/DD/YYYY');
                    //ed=end.format('MM/DD/YYYY');
                    //Index.initJQVMAP(); // init index page's custom scripts
                    //Index.pieChart("Hadoop");
                    //Index.initCharts("Hadoop");
                    //Index.dayCharts();
                    //Index.timeCharts();
                }
            );


            $('#dashboard-report-range span').html(moment().format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
            $('#dashboard-report-range').show();
            var d=moment().format('MMMM D, YYYY');
            var td=moment().format('MMMM D, YYYY');
            //alert(td);
            getRequests(d,td);
            getNewRequests(d,td);
          
            //st=moment().format('MM/DD/YYYY');
            //ed= moment().format('MM/DD/YYYY');
            //Index.initJQVMAP(); // init index page's custom scripts
            //Index.pieChart("Hadoop");
            //Index.initCharts("Hadoop");
            //Index.dayCharts();
            //Index.timeCharts();
        },

        initIntro: function () {

            // display marketing alert only once
            if (!$.cookie('intro_show')) {
                setTimeout(function () {
                    var unique_id = $.gritter.add({
                        // (string | mandatory) the heading of the notification
                        title: '<a href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes" target="_blank">Get Metronic Just For 25$</a>',
                        // (string | mandatory) the text inside the notification
                        text: 'Metronic is World\'s #1 Selling Bootstrap 3 Admin Theme Ever! 18000+ Users Can\'t be Wrong.',
                        // (string | optional) the image to display on the left
                        image: '../../assets/admin/layout/img/avatar1.jpg',
                        // (bool | optional) if you want it to fade out on its own or just sit there
                        sticky: true,
                        // (int | optional) the time you want it to be alive for before fading out
                        time: '',
                        // (string | optional) the class name you want to apply to that specific message
                        class_name: 'my-sticky-class'
                    });

                    // You can have it return a unique id, this can be used to manually remove it later using
                    setTimeout(function () {
                        $.gritter.remove(unique_id, {
                            fade: true,
                            speed: 'slow'
                        });
                    }, 12000);
                }, 2000);

                setTimeout(function () {
                    var unique_id = $.gritter.add({
                        // (string | mandatory) the heading of the notification
                        title: '<a href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes" target="_blank">Develop with Metronic!</a>',
                        // (string | mandatory) the text inside the notification
                        text: 'Metronic comes with 160+ templates, 70+ plugins and 500+ UI components. Also 3 Frontend Themes, Corporate Frontend, eCommerce Frontend and One Page Parallax Frontend are included. Buy 1 Get 4!',
                        // (string | optional) the image to display on the left
                        image: '../../assets/admin/layout/img/avatar1.jpg',
                        // (bool | optional) if you want it to fade out on its own or just sit there
                        sticky: true,
                        // (int | optional) the time you want it to be alive for before fading out
                        time: '',
                        // (string | optional) the class name you want to apply to that specific message
                        class_name: 'my-sticky-class'
                    });

                    // You can have it return a unique id, this can be used to manually remove it later using
                    setTimeout(function () {
                        $.gritter.remove(unique_id, {
                            fade: true,
                            speed: 'slow'
                        });
                    }, 13000);
                }, 8000);

                setTimeout(function () {

                    $('#styler').pulsate({
                        color: "#bb3319",
                        repeat: 10
                    });

                    $.extend($.gritter.options, {
                        position: 'top-left'
                    });

                    var unique_id = $.gritter.add({
                        position: 'top-left',
                        // (string | mandatory) the heading of the notification
                        title: '<a href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes" target="_blank">Customize Metronic!</a>',
                        // (string | mandatory) the text inside the notification
                        text: 'Metronic allows you to easily customize the theme with unlimited layout options and color schemes. Try Metronic Today!',
                        // (string | optional) the image to display on the left
                        image1: './assets/img/avatar1.png',
                        // (bool | optional) if you want it to fade out on its own or just sit there
                        sticky: true,
                        // (int | optional) the time you want it to be alive for before fading out
                        time: '',
                        // (string | optional) the class name you want to apply to that specific message
                        class_name: 'my-sticky-class'
                    });

                    $.extend($.gritter.options, {
                        position: 'top-right'
                    });

                    // You can have it return a unique id, this can be used to manually remove it later using
                    setTimeout(function () {
                        $.gritter.remove(unique_id, {
                            fade: true,
                            speed: 'slow'
                        });
                    }, 10000);

                }, 23000);

                $.cookie('intro_show', 1);
            }
        }

    };

}();