var ChatQueryEditable = function () {
	
    $.mockjaxSettings.responseTime = 500;
    
    	var updateName=function(email,name)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					name: name,
    		           jEventName: "U_Name"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var updateEmail=function(email,email_id)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					email_id: email_id,
    		           jEventName: "U_Email"
    		         },
    		         function(data,status){
    		        	$('#email_id').val(email_id);
    		         });
    		
    	}
    	var updateEmailStatus=function(email,ecorrect)
    	{
    		/*$.get("FreeServlet",
    		         {
    					email: email,
    					ecorrect: ecorrect,
    		           jEventName: "U_EmailStatus"
    		         },
    		         function(data,status){
    		        	
    		         });*/
    		
    	}
    	var updateEmailCampaign=function(email,course,name)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					course: course,
    					name: name,
    		            jEventName: "Update_Campaign"
    		         },
    		         function(data,status){
    		        	
    		         });
    		
    	}
    	var updatePhone=function(email,phone)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					phone: phone,
    		           jEventName: "U_Phone"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var updatePhoneStatus=function(email,mcorrect)
    	{
    		/*$.get("FreeServlet",
    		         {
    					email: email,
    					mcorrect: mcorrect,
    		           jEventName: "U_PhoneStatus"
    		         },
    		         function(data,status){
    		         	
    		         });*/
    		
    	}
    	var updateSex=function(email,sex)
    	{
    		/*$.get("FreeServlet",
    		         {
    					email: email,
    					sex: sex,
    		           jEventName: "U_Sex"
    		         },
    		         function(data,status){
    		         	
    		         });*/
    		
    	}
    	var updateAttend=function(email,attend)
    	{
    		/*$.get("FreeServlet",
    		         {
    					email: email,
    					attend: attend,
    		           jEventName: "U_Attend"
    		         },
    		         function(data,status){
    		         	if(attend=="y")
    		         		$('#attended').val('Attended');
    		         	else if(attend=="n")
    		         		$('#attended').val('Not Attended');
    		         	else
    		         		$('#attended').val(' ');
    		         		
    		         });*/
    		
    	}
    	
    	var updateWork=function(email,work)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					work: work,
    		           jEventName: "U_Work"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var updateCompany=function(email,company)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					company: company,
    		           jEventName: "U_Company"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var updateCountry=function(email,country)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					country: country,
    		           jEventName: "U_Country"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var updateNextTimeCall=function(email,nextcall)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					nextcall: nextcall,
    		           jEventName: "U_NextTimeCall"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var updateNextWebinar=function(email,nextwebinar,time,id)
    	{
    		$('#response_loading').show();
    		$.get("ChatQuery",
    		         {
    					email: email,
    					nextwebinar: nextwebinar,
    					id: id,
    					time: time,
    		           jEventName: "U_NextWebinar"
    		         },
    		         function(data,status){
    		        	 $('#response_loading').hide();
    		         	 $('#hero').show();
    		         	 $('#hero').html(data);
    		         });
    		
    	}
    	var updateInterested=function(email,interested)
    	{
    		$.get("ChatQuery",
    		         {
    					email: email,
    					interested: interested,
    		           jEventName: "U_Interest"
    		         },
    		         function(data,status){
    		         	
    		         });
    		
    	}
    	var sentMessage=function(messageValue,mobileNumber)
    	{
    		$.get('http://api.znisms.com/post/smsv3.asp',
    				{
    					userid: 'gyansha',
    					apikey: '2bf89e9b01000a6572605bccd4f57b83',
    					message:messageValue,
    					senderid:'GYANSHA',
    					sendto:mobileNumber
    				}).done(function(){
    					alert("Sent Message");
    				});
    	}
    var log = function (settings, response) {
        var s = [],
            str;
        s.push(settings.type.toUpperCase() + ' url = "' + settings.url + '"');
        for (var a in settings.data) {
            if (settings.data[a] && typeof settings.data[a] === 'object') {
                str = [];
                for (var j in settings.data[a]) {
                    str.push(j + ': "' + settings.data[a][j] + '"');
                    
                }
                str = '{ ' + str.join(', ') + ' }';
            } else {
                str = '"' + settings.data[a] + '"';
                
            }
            s.push(a + ' = ' + str);
            
            
        }
        
       
        var email=$('#email_id').val();
       
        if(settings.data['name']==='name')
        	updateName(email,settings.data['value']);
        else if(settings.data['name']==='email')
        	updateEmail(email,settings.data['value']);
        else if(settings.data['name']==='phone')
        {
        	$("#phoneNumber").val((settings.data['value']).trim());
        	updatePhone(email,settings.data['value']);
        }
        else if(settings.data['name']==='attend')
        	updateAttend(email,settings.data['value']);
        else if(settings.data['name']==='ecorrect')
        	{
        		updateEmailStatus(email,settings.data['value']);
        			var course=$('#courseid').val();
        			var name=$('#studentname').val();
        			updateEmailCampaign(email,course,name);
        	}
        else if(settings.data['name']==='mcorrect')
        	updatePhoneStatus(email,settings.data['value']);
        else if(settings.data['name']==='query')
        	$('#adminquery').val(settings.data['value']);
        else if(settings.data['name']==='sendMessage')
        {
        	sentMessage(settings.data['value'],($("#phoneNumber").val()).trim());
        	$('#adminquery').val("Message Sent: "+settings.data['value']);
        }
        else if(settings.data['name']==='work')
        	updateWork(email,settings.data['value']);
        else if(settings.data['name']==='country')
        	updateCountry(email,settings.data['value']);
        else if(settings.data['name']==='company')
        	updateCompany(email,settings.data['value']);
        else if(settings.data['name']==='meeting_start')
        	updateNextTimeCall(email,settings.data['value']);
        else if(settings.data['name']==='vacation')
        	{
        		var id=$('#responseid').val();
        		var today = new Date();
        		var hour    = today.getHours();
        		var minute  = today.getMinutes();
        		var second  = today.getSeconds();
        		if(hour.toString().length == 1) 
        		{
        			var hour = '0'+hour;
        		}
        		if(minute.toString().length == 1) 
        		{
        			var minute = '0'+minute;
        		}
        		if(second.toString().length == 1)
        		{
                var second = '0'+second;
        		} 
        		var time=hour+':'+minute+':'+second;
        		updateNextWebinar(email,settings.data['value'],time,id);
        	}
        else if(settings.data['name']==='callinterest')
        	updateInterested(email,settings.data['value']);
        else if(settings.data['name']==='sex')
        	updateSex(email,settings.data['value']);
        
        	
        s.push('RESPONSE: status = ' + response.status);
        
      
        if (response.responseText) {
            if ($.isArray(response.responseText)) {
                s.push('[');
                $.each(response.responseText, function (i, v) {
                    s.push('{value: ' + v.value + ', text: "' + v.text + '"}');
                });
                s.push(']');
            } else {
                s.push($.trim(response.responseText));
            }
        }
        s.push('--------------------------------------\n');
        $('#console').val(s.join('\n') + $('#console').val());
    }

    var initAjaxMock = function () {
        //ajax mocks

        $.mockjax({
            url: '/post',
            response: function (settings) {
                log(settings, this);
                
            }
        });

        $.mockjax({
            url: '/error',
            status: 400,
            statusText: 'Bad Request',
            response: function (settings) {
                this.responseText = 'Please input correct value';
                log(settings, this);
            }
        });

        $.mockjax({
            url: '/status',
            status: 500,
            response: function (settings) {
                this.responseText = 'Internal Server Error';
                log(settings, this);
            }
        });

        
       

    }

    var initEditables = function () {

        //set editable mode based on URL parameter
        if (Metronic.getURLParameter('mode') == 'inline') {
            $.fn.editable.defaults.mode = 'inline';
            $('#inline').attr("checked", true);
            jQuery.uniform.update('#inline');
        } else {
            $('#inline').attr("checked", false);
            jQuery.uniform.update('#inline');
        }

        //global settings 
        $.fn.editable.defaults.inputclass = 'form-control';
        $.fn.editable.defaults.url = '/post';

        //editables element samples 
        $('#name').editable({
            url: '/post',
            type: 'text',
            pk: 1,
            name: 'name',
            title: 'Enter name'
        });
        $('#email').editable({
            url: '/post',
            type: 'text',
            pk: 1,
            name: 'email',
            title: 'Enter email'
        });
        $('#company').editable({
            url: '/post',
            type: 'text',
            pk: 1,
            name: 'company',
            title: 'Enter company name'
        });
        $('#work').editable({
            url: '/post',
            type: 'text',
            pk: 1,
            name: 'work',
            title: 'What does?'
        });
        $('#phone').editable({
            validate: function (value) {
                if ($.trim(value) == '') return 'This field is required';
            }
        });
        $('#sex').editable({
            prepend: "not selected",
            inputclass: 'form-control',
            source: [{
                    value: 'Male',
                    text: 'Male'
                }, {
                    value: 'Female',
                    text: 'Female'
                }
            ],
            display: function (value, sourceData) {
                var colors = {
                    "": "gray",
                    "Male": "green",
                    "Female": "blue"
                },
                    elem = $.grep(sourceData, function (o) {
                        return o.value == value;
                    });

                if (elem.length) {
                    $(this).text(elem[0].text).css("color", colors[value]);
                } else {
                    $(this).empty();
                }
            }
        });
        $('#mcorrect').editable({
            prepend: "Uncheck",
            inputclass: 'form-control',
            source: [{
                    value: 'y',
                    text: 'Correct'
                }, {
                    value: 'n',
                    text: 'Incorrect'
                }
            ],
            display: function (value, sourceData) {
                var colors = {
                    "": "gray",
                    "y": "green",
                    "n": "red"
                },
                    elem = $.grep(sourceData, function (o) {
                        return o.value == value;
                    });

                if (elem.length) {
                    $(this).text(elem[0].text).css("color", colors[value]);
                } else {
                    $(this).empty();
                }
            }
        });
        $('#ecorrect').editable({
            prepend: "Uncheck",
            inputclass: 'form-control',
            source: [{
                    value: 'y',
                    text: 'Correct'
                }, {
                    value: 'n',
                    text: 'Incorrect'
                }
            ],
            display: function (value, sourceData) {
                var colors = {
                    "": "gray",
                    "y": "green",
                    "n": "red"
                },
                    elem = $.grep(sourceData, function (o) {
                        return o.value == value;
                    });

                if (elem.length) {
                    $(this).text(elem[0].text).css("color", colors[value]);
                } else {
                    $(this).empty();
                }
            }
        });
        $('#callinterest').editable({
            inputclass: 'form-control',
            source: [{
                    value: 'y',
                    text: 'Interested'
                }, {
                    value: 'n',
                    text: 'Not Interested'
                }
            ],
            display: function (value, sourceData) {
                var colors = {
                    "y": "green",
                    "n": "red"
                },
                    elem = $.grep(sourceData, function (o) {
                        return o.value == value;
                    });

                if (elem.length) {
                    $(this).text(elem[0].text).css("color", colors[value]);
                } else {
                    $(this).empty();
                }
            }
        });
        $('#attend').editable({
            prepend: "Select",
            inputclass: 'form-control',
            source: [{
                    value: 'y',
                    text: 'Attended'
                }, {
                    value: 'n',
                    text: 'Not Attended'
                }
            ],
            display: function (value, sourceData) {
                var colors = {
                    "": "gray",
                    "y": "green",
                    "n": "red"
                },
                    elem = $.grep(sourceData, function (o) {
                        return o.value == value;
                    });

                if (elem.length) {
                    $(this).text(elem[0].text).css("color", colors[value]);
                } else {
                    $(this).empty();
                }
            }
        });
        
        $('#status').editable();
        $('#act').editable();
        
        
        $('#vacation').editable({
            rtl : Metronic.isRTL() 
        });

        $('#dob').editable({
            inputclass: 'form-control',
        });

        $('#event').editable({
            placement: (Metronic.isRTL() ? 'left' : 'right'),
            combodate: {
                firstItem: 'name'
            }
        });

        $('#meeting_start').editable({
            format: 'yyyy-mm-dd hh:ii',
            viewformat: 'dd/mm/yyyy hh:ii',
            validate: function (v) {
                if (v && v.getDate() == 10) return 'Day cant be 10!';
            },
            datetimepicker: {
                rtl : Metronic.isRTL(),
                todayBtn: 'linked',
                weekStart: 1
            }
        });

        $('#query').editable({
            showbuttons: 'bottom'
        });
        
        $('#sendMessage').editable({
            showbuttons: 'bottom'
        });

        $('#note').editable({
            showbuttons : (Metronic.isRTL() ? 'left' : 'right')
        });

        $('#pencil').click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            $('#note').editable('toggle');
        });

        $('#state').editable({
            source: ["Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Dakota", "North Carolina", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"]
        });

        $('#int').editable({
            pk: 1,
            limit: 3,
            source: [{
                    value: 1,
                    text: '1'
                }, {
                    value: 2,
                    text: '2'
                }, {
                    value: 3,
                    text: '3'
                }, {
                    value: 4,
                    text: '4'
                }, {
                    value: 5,
                    text: '5'
                }
            ]
        });

        $('#int').on('shown', function(e, reason) {
            Metronic.initUniform();
        });

        $('#tags').editable({
            inputclass: 'form-control input-medium',
            select2: {
                tags: ['html', 'javascript', 'css', 'ajax'],
                tokenSeparators: [",", " "]
            }
        });

        var countries = [];
        $.each({
            "BD": "Bangladesh",
            "BE": "Belgium",
            "BF": "Burkina Faso",
            "BG": "Bulgaria",
            "BA": "Bosnia and Herzegovina",
            "BB": "Barbados",
            "WF": "Wallis and Futuna",
            "BL": "Saint Bartelemey",
            "BM": "Bermuda",
            "BN": "Brunei Darussalam",
            "BO": "Bolivia",
            "BH": "Bahrain",
            "BI": "Burundi",
            "BJ": "Benin",
            "BT": "Bhutan",
            "JM": "Jamaica",
            "BV": "Bouvet Island",
            "BW": "Botswana",
            "WS": "Samoa",
            "BR": "Brazil",
            "BS": "Bahamas",
            "JE": "Jersey",
            "BY": "Belarus",
            "O1": "Other Country",
            "LV": "Latvia",
            "RW": "Rwanda",
            "RS": "Serbia",
            "TL": "Timor-Leste",
            "RE": "Reunion",
            "LU": "Luxembourg",
            "TJ": "Tajikistan",
            "RO": "Romania",
            "PG": "Papua New Guinea",
            "GW": "Guinea-Bissau",
            "GU": "Guam",
            "GT": "Guatemala",
            "GS": "South Georgia and the South Sandwich Islands",
            "GR": "Greece",
            "GQ": "Equatorial Guinea",
            "GP": "Guadeloupe",
            "JP": "Japan",
            "GY": "Guyana",
            "GG": "Guernsey",
            "GF": "French Guiana",
            "GE": "Georgia",
            "GD": "Grenada",
            "GB": "United Kingdom",
            "GA": "Gabon",
            "SV": "El Salvador",
            "GN": "Guinea",
            "GM": "Gambia",
            "GL": "Greenland",
            "GI": "Gibraltar",
            "GH": "Ghana",
            "OM": "Oman",
            "TN": "Tunisia",
            "JO": "Jordan",
            "HR": "Croatia",
            "HT": "Haiti",
            "HU": "Hungary",
            "HK": "Hong Kong",
            "HN": "Honduras",
            "HM": "Heard Island and McDonald Islands",
            "VE": "Venezuela",
            "PR": "Puerto Rico",
            "PS": "Palestinian Territory",
            "PW": "Palau",
            "PT": "Portugal",
            "SJ": "Svalbard and Jan Mayen",
            "PY": "Paraguay",
            "IQ": "Iraq",
            "PA": "Panama",
            "PF": "French Polynesia",
            "BZ": "Belize",
            "PE": "Peru",
            "PK": "Pakistan",
            "PH": "Philippines",
            "PN": "Pitcairn",
            "TM": "Turkmenistan",
            "PL": "Poland",
            "PM": "Saint Pierre and Miquelon",
            "ZM": "Zambia",
            "EH": "Western Sahara",
            "RU": "Russian Federation",
            "EE": "Estonia",
            "EG": "Egypt",
            "TK": "Tokelau",
            "ZA": "South Africa",
            "EC": "Ecuador",
            "IT": "Italy",
            "VN": "Vietnam",
            "SB": "Solomon Islands",
            "EU": "Europe",
            "ET": "Ethiopia",
            "SO": "Somalia",
            "ZW": "Zimbabwe",
            "SA": "Saudi Arabia",
            "ES": "Spain",
            "ER": "Eritrea",
            "ME": "Montenegro",
            "MD": "Moldova, Republic of",
            "MG": "Madagascar",
            "MF": "Saint Martin",
            "MA": "Morocco",
            "MC": "Monaco",
            "UZ": "Uzbekistan",
            "MM": "Myanmar",
            "ML": "Mali",
            "MO": "Macao",
            "MN": "Mongolia",
            "MH": "Marshall Islands",
            "MK": "Macedonia",
            "MU": "Mauritius",
            "MT": "Malta",
            "MW": "Malawi",
            "MV": "Maldives",
            "MQ": "Martinique",
            "MP": "Northern Mariana Islands",
            "MS": "Montserrat",
            "MR": "Mauritania",
            "IM": "Isle of Man",
            "UG": "Uganda",
            "TZ": "Tanzania, United Republic of",
            "MY": "Malaysia",
            "MX": "Mexico",
            "IL": "Israel",
            "FR": "France",
            "IO": "British Indian Ocean Territory",
            "FX": "France, Metropolitan",
            "SH": "Saint Helena",
            "FI": "Finland",
            "FJ": "Fiji",
            "FK": "Falkland Islands (Malvinas)",
            "FM": "Micronesia, Federated States of",
            "FO": "Faroe Islands",
            "NI": "Nicaragua",
            "NL": "Netherlands",
            "NO": "Norway",
            "NA": "Namibia",
            "VU": "Vanuatu",
            "NC": "New Caledonia",
            "NE": "Niger",
            "NF": "Norfolk Island",
            "NG": "Nigeria",
            "NZ": "New Zealand",
            "NP": "Nepal",
            "NR": "Nauru",
            "NU": "Niue",
            "CK": "Cook Islands",
            "CI": "Cote d'Ivoire",
            "CH": "Switzerland",
            "CO": "Colombia",
            "CN": "China",
            "CM": "Cameroon",
            "CL": "Chile",
            "CC": "Cocos (Keeling) Islands",
            "CA": "Canada",
            "CG": "Congo",
            "CF": "Central African Republic",
            "CD": "Congo, The Democratic Republic of the",
            "CZ": "Czech Republic",
            "CY": "Cyprus",
            "CX": "Christmas Island",
            "CR": "Costa Rica",
            "CV": "Cape Verde",
            "CU": "Cuba",
            "SZ": "Swaziland",
            "SY": "Syrian Arab Republic",
            "KG": "Kyrgyzstan",
            "KE": "Kenya",
            "SR": "Suriname",
            "KI": "Kiribati",
            "KH": "Cambodia",
            "KN": "Saint Kitts and Nevis",
            "KM": "Comoros",
            "ST": "Sao Tome and Principe",
            "SK": "Slovakia",
            "KR": "Korea, Republic of",
            "SI": "Slovenia",
            "KP": "Korea, Democratic People's Republic of",
            "KW": "Kuwait",
            "SN": "Senegal",
            "SM": "San Marino",
            "SL": "Sierra Leone",
            "SC": "Seychelles",
            "KZ": "Kazakhstan",
            "KY": "Cayman Islands",
            "SG": "Singapore",
            "SE": "Sweden",
            "SD": "Sudan",
            "DO": "Dominican Republic",
            "DM": "Dominica",
            "DJ": "Djibouti",
            "DK": "Denmark",
            "VG": "Virgin Islands, British",
            "DE": "Germany",
            "YE": "Yemen",
            "DZ": "Algeria",
            "US": "United States",
            "UY": "Uruguay",
            "YT": "Mayotte",
            "UM": "United States Minor Outlying Islands",
            "LB": "Lebanon",
            "LC": "Saint Lucia",
            "LA": "Lao People's Democratic Republic",
            "TV": "Tuvalu",
            "TW": "Taiwan",
            "TT": "Trinidad and Tobago",
            "TR": "Turkey",
            "LK": "Sri Lanka",
            "LI": "Liechtenstein",
            "A1": "Anonymous Proxy",
            "TO": "Tonga",
            "LT": "Lithuania",
            "A2": "Satellite Provider",
            "LR": "Liberia",
            "LS": "Lesotho",
            "TH": "Thailand",
            "TF": "French Southern Territories",
            "TG": "Togo",
            "TD": "Chad",
            "TC": "Turks and Caicos Islands",
            "LY": "Libyan Arab Jamahiriya",
            "VA": "Holy See (Vatican City State)",
            "VC": "Saint Vincent and the Grenadines",
            "AE": "United Arab Emirates",
            "AD": "Andorra",
            "AG": "Antigua and Barbuda",
            "AF": "Afghanistan",
            "AI": "Anguilla",
            "VI": "Virgin Islands, U.S.",
            "IS": "Iceland",
            "IR": "Iran, Islamic Republic of",
            "AM": "Armenia",
            "AL": "Albania",
            "AO": "Angola",
            "AN": "Netherlands Antilles",
            "AQ": "Antarctica",
            "AP": "Asia/Pacific Region",
            "AS": "American Samoa",
            "AR": "Argentina",
            "AU": "Australia",
            "AT": "Austria",
            "AW": "Aruba",
            "IN": "India",
            "AX": "Aland Islands",
            "AZ": "Azerbaijan",
            "IE": "Ireland",
            "ID": "Indonesia",
            "UA": "Ukraine",
            "QA": "Qatar",
            "MZ": "Mozambique",
            "HO": "Hogwarts"
        }, function (k, v) {
            countries.push({
                id: k,
                text: v
            });
        });

        $('#country').editable({
            inputclass: 'form-control input-small',
            source: countries
        });

        $('#address').editable({
            url: '/post',
            value: {
                city: "San Francisco",
                street: "Valencia",
                building: "#24"
            },
            validate: function (value) {
                if (value.city == '') return 'city is required!';
            },
            display: function (value) {
                if (!value) {
                    $(this).empty();
                    return;
                }
                var html = '<b>' + $('<div>').text(value.city).html() + '</b>, ' + $('<div>').text(value.street).html() + ' st., bld. ' + $('<div>').text(value.building).html();
                $(this).html(html);
            }
        });
    }

    return {
        //main function to initiate the module
        init: function () {

            // inii ajax simulation
            initAjaxMock();

            // init editable elements
            initEditables();
            
            // init editable toggler
            $('#enable').click(function () {
                $('#user .editable').editable('toggleDisabled');
            });

            // init 
            $('#inline').on('change', function (e) {
                if ($(this).is(':checked')) {
                    window.location.href = 'form_editable.html?mode=inline';
                } else {
                    window.location.href = 'form_editable.html';
                }
            });

            // handle editable elements on hidden event fired
            $('#user .editable').on('hidden', function (e, reason) {
                if (reason === 'save' || reason === 'nochange') {
                    var $next = $(this).closest('tr').next().find('.editable');
                    if ($('#autoopen').is(':checked')) {
                        setTimeout(function () {
                            $next.editable('show');
                        }, 300);
                    } else {
                        $next.focus();
                    }
                }
            });


        },
        
			ddd:function(){
			        	
			        	alert("saurabh");
			        }
    };

}();