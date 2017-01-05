var time=[],line1=[],line2=[],line3=[],line4=[],line5=[];
$.ajaxSettings.async=false;
$.ajax({
	type:"get",
	url:'/bi/api/reports/linedata1?circle=week',
	async:false,
	dataType:"json",
	success:function(data){
		for(var i = 0 ; i < data.length ;i++){
			time.push(data[i]['日期']);
			line1.push(data[i]['累计注册药店数量']);
			line2.push(data[i]['新增注册药店数量']);
			line3.push(data[i]['新增使用药店数量']);
			line4.push(data[i]['活跃药店数量']);
			line5.push(data[i]['上传单据药店数']);
		}
    }
});
var option2 = {
    title: {
        text: '全国48周情况'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
    	orient: 'horizontal',
        top: 'auto',
        itemHeight:14,
        itemWidth:14,
        data:['累计注册药店数量','新增注册药店数量','新增使用药店数量','活跃药店数量','上传单据药店数']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: time
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'累计注册药店数量',
            type:'line',
            stack: '总量',
            data:line1
        },
        {
            name:'新增注册药店数量',
            type:'line',
            stack: '总量',
            data:line2
        },
        {
            name:'新增使用药店数量',
            type:'line',
            stack: '总量',
            data:line3
        },
        {
            name:'活跃药店数量',
            type:'line',
            stack: '总量',
            data:line4
        },
        {
            name:'上传单据药店数',
            type:'line',
            stack: '总量',
            data:line5
        }
    ]
};
var chart2 = echarts.init(document.getElementById('main2'));
chart2.setOption(option2);