var time=[],line1=[],line1Max=0,line2=[],line2Max=0;
$.ajaxSettings.async=false;
$.ajax({
	type:"get",
	url:'/bi/api/reports/linedata2?circle=week',
	async:false,
	dataType:"json",
	success:function(data){
		for(var i = 0 ; i < data.length ;i++){
			time.push(data[i]['日期']);
			if(data[i]['销售总额'] > line1Max){
				line1Max = data[i]['销售总额']; 
			}
			if(data[i]['销售单据量'] > line2Max){
				line2Max = data[i]['销售单据量'];
			}
			line1.push(data[i]['销售总额']);
			line2.push(data[i]['销售单据量']);
		}
    }
});
var option3 = {
    title: {
        text: '全国48周销售情况'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['销售总额','销售单据量']
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: true,
            data: time
        },
        {
            type: 'category',
            boundaryGap: true,
            data: []
        }
    ],
    yAxis: [
    	{
            type: 'value',
            scale: true,
            name: '销售单据量',
            max: line2Max,
            min: 0,
            boundaryGap: [0.2, 0.2]
        },
        {
            type: 'value',
            scale: true,
            name: '销售总额',
            max: line1Max,
            min: 0,
            boundaryGap: [0.2, 0.2]
        }
        
    ],
    series: [
        {
            name:'销售总额',
            type:'bar',
            xAxisIndex: 1,
            yAxisIndex: 1,
            data:line1,
            itemStyle: {
	            normal: {
	                color: '#b4dffe'
	            },
	            emphasis: {
	                color: '#61b8f2'
	            }
	        }
        },
        {
            name:'销售单据量',
            type:'line',
            data:line2
        }
    ]
};
var chart3 = echarts.init(document.getElementById('main3'));
chart3.setOption(option3);