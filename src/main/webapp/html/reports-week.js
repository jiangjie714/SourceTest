function randomData() {
    return Math.round(Math.random()*1000);
}
var content1=[],content2=[],content3=[],content4=[],content5=[],content6=[],content7=[];
var contentMax1 = 0,contentMax2 = 0,contentMax3 = 0,contentMax4 = 0,contentMax5 = 0,contentMax6 = 0,contentMax7 = 0;
$.ajaxSettings.async=false;
$.ajax({
	type:"get",
	url:'/bi/api/reports/mapdata?circle=week&region=state',
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
     		var provice =  data[i]['省'];
     		if(provice == "内蒙古自治区"){
     			provice = "内蒙古";
     		}else if(provice == "广西壮族自治区"){
     			provice = "广西";
     		}else{
	     		provice = provice.substring(0,provice.length-1);
     		}
     		if(data[i]['累计注册药店数量'] > contentMax1){
     			contentMax1 = data[i]['累计注册药店数量'];
     		}
     		if(data[i]['新增注册药店数量'] > contentMax2){
     			contentMax2 = data[i]['新增注册药店数量'];
     		}
     		if(data[i]['新增使用药店数量'] > contentMax3){
     			contentMax3 = data[i]['新增使用药店数量'];
     		}
     		if(data[i]['活跃药店数量'] > contentMax4){
     			contentMax4 = data[i]['活跃药店数量'];
     		}
     		if(data[i]['上传单据药店数'] > contentMax5){
     			contentMax5 = data[i]['上传单据药店数'];
     		}
     		if(data[i]['销售总额'] > contentMax6){
     			contentMax6 = data[i]['销售总额'];
     		}
     		if(data[i]['销售单据量'] > contentMax7){
     			contentMax7 = data[i]['销售单据量'];
     		}
     		content1.push(new d(provice,data[i]['累计注册药店数量']));
     		content2.push(new d(provice,data[i]['新增注册药店数量']));
     		content3.push(new d(provice,data[i]['新增使用药店数量']));
     		content4.push(new d(provice,data[i]['活跃药店数量']));
     		content5.push(new d(provice,data[i]['上传单据药店数']));
     		content6.push(new d(provice,data[i]['销售总额']));
     		content7.push(new d(provice,data[i]['销售单据量']));
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
var option = {
    title: {
        text: '上周全国省图情况',
        left: 'left'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'horizontal',
        top: 'auto',
        itemHeight:14,
        itemWidth:14,
        selectedMode:'single',
        data:['累计注册药店数量','新增注册药店数量','新增使用药店数量','活跃药店数量','上传单据药店数','销售总额','销售单据量']       
    },
    visualMap: {
    	type:'piecewise',
        min: 0,
        max: contentMax1,
        splitNumber: 5,
        left: 'left',
        top: 'bottom',
        color: color1.reverse(),
        textStyle: {
            color: 'black'
        },
        calculable: true
    },
    series: [
        {
            name: '累计注册药店数量',
            type: 'map',
            mapType: 'china',
            roam: false,
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content1
        },
        {
            name: '新增注册药店数量',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content2
        },
        {
            name: '新增使用药店数量',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content3
        },
        {
            name: '活跃药店数量',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content4
        },
        {
            name: '上传单据药店数',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content5
        },
        {
            name: '销售总额',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content6
        },
        {
            name: '销售单据量',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showLegendSymbol: false,
            data:content7
        }
    ]
};
$(function(){
	$.get('./china.json', function (chinaJson) {
		echarts.registerMap('china', chinaJson);
		var chart = echarts.init(document.getElementById('main'));
		chart.setOption(option);
		chart.on('legendselectchanged', function (params) {
		   	//更新数据
	      	var option = chart.getOption();
		   	if(params.name == "新增注册药店数量"){
		      	option.visualMap[0].max = contentMax2;
		      	option.visualMap[0].color = color2.reverse();  
		   	}else if(params.name == "累计注册药店数量"){
		   		option.visualMap[0].max = contentMax1;
		   		option.visualMap[0].color = color1.reverse();
		   	}else if(params.name == "新增使用药店数量"){
		   		option.visualMap[0].max = contentMax3;
		   		option.visualMap[0].color = color3.reverse();
		   	}else if(params.name == "活跃药店数量"){
		   		option.visualMap[0].max = contentMax4;
		   		option.visualMap[0].color = color4.reverse();
		   	}else if(params.name == "上传单据药店数"){
		   		option.visualMap[0].max = contentMax5;
		   		option.visualMap[0].color = color5.reverse();
		   	}else if(params.name == "销售总额"){
		   		option.visualMap[0].max = contentMax6;
		   		option.visualMap[0].color = color6.reverse();
		   	}else if(params.name == "销售单据量"){
		   		option.visualMap[0].max = contentMax7;
		   		option.visualMap[0].color = color7.reverse();
		   	}
	      	chart.setOption(option); 
		});
	});
});