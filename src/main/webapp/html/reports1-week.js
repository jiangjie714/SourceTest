var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};
var content=[],geoCoordMap={};
$.ajaxSettings.async=false;
$.get('./d.json', function (city) {
	var d = function(name,value){
		return {
			name:value
		};
	}
	for(var i = 0 ; i < city.length ;i++){
		for(var j = 0 ; j < city[i].children.length; j++){
			var ll = [];
			ll.push(parseFloat(city[i].children[j].log));
			ll.push(parseFloat(city[i].children[j].lat));
			var name = city[i].children[j].name;
			geoCoordMap[name]=ll;
		}
	}
});
$.ajax({
	type:"get",
	url:'/bi/api/reports/mapdata?circle=week&region=city',
	async:false,
	dataType:"json",
	success:function(data){
		var d = function(name,value){
			return {
				name:name,
				value:value
			};
		}
     	for(var i = 0 ; i < data.length ;i++){
     		var city =  data[i]['市'];
     		city = city.substring(0,city.length-1);
     		var value = "<br/>累计注册药店数量:"+data[i]['累计注册药店数量']+"<br/>"+
     					"新增注册药店数量:"+data[i]['新增注册药店数量']+"<br/>"+
     					"新增使用药店数量:"+data[i]['新增使用药店数量']+"<br/>"+
     					"活跃药店数量:"+data[i]['活跃药店数量']+"<br/>"+
     					"上传单据药店数:"+data[i]['上传单据药店数']+"<br/>"+
     					"销售总额:"+data[i]['销售总额']+"<br/>"+
     					"销售单据量:"+data[i]['销售单据量']+"<br/>";
     		content.push(new d(city,value));
     	}
    }
});
var color1=['#b4dffe','#61b8f2','#29a4fc','#1976bd','#0b4c80'],
	color2=['#b6ddfc','#70bdf6','#2c9cf6','#1a7cc2','#0b477a'],
	color3=['#c6eded','#92d9dc','#58c0c8','#429a97','#226060'],
	color4=['#c8ecca','#85d48c','#59c362','#389640','#235d27'],
	color5=['#e1f0c3','#c5df8b','#a4d152','#7ca237','#506620'],
	color6=['#b8e7f8','#6ecef2','#36baee','#228ebb','#125977'],
	color7=['#f6d7b0','#fbbb6c','#fd9d31','#cc751e','#884618'];
var option1 = {
    backgroundColor: 'white',
    title: {
        text: '上周全国市图情况',
        left: 'left'
    },
    tooltip: {
        trigger: 'item',
        formatter: function (params) {
            return params.name + ' : ' + params.value[2];
        }
    },
    legend: {
        orient: 'horizontal',
        top: 'auto',
        itemHeight:14,
        itemWidth:14,
        selectedMode:'single',
        data:['累计注册药店数量','新增注册药店数量','新增使用药店数量','活跃药店数量','上传单据药店数','销售总额','销售单据量']
    },
    
    geo: {
        map: 'china',
        label: {
            emphasis: {
                show: false
            }
        },
        itemStyle: {
            normal: {
                areaColor: '#b4dffe',
                borderColor: '#111'
            },
            emphasis: {
                areaColor: '#61b8f2'
            }
        }
    },
    series: [
        {
            name: 'pm2.5',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: convertData(content),
            symbolSize: 8,
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            itemStyle: {
                emphasis: {
                    borderColor: '#fff',
                    borderWidth: 1
                }
            }
        }
    ]
}
$(function(){
	$.get('./china.json', function (chinaJson) {
		echarts.registerMap('china', chinaJson);
		var chart2 = echarts.init(document.getElementById('main1'));
		chart2.setOption(option1);
	});
});