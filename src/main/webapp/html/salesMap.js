var map;
var mapIcon;
var mapGeo;
var showOnlineUsers = true;
var showInitUsers = true;
var list;
$(function() {
	initMap(); // 初始化地图
	getUsers();
});

function query() {
	clearMap();
	getUsers();
}

function getUsers() {
	$.get("/bi/api/reports/salesForMap", fillUser, "json");
}

// 填充在线用户数据
function fillUser(data) {
	if (data.length > 0) {
		for (var i = 0; i < data.length; i++) {
			var order = data[i];
			var content = "<h3 style='border-bottom:1px solid #aaa;'>" + order.name + "</h3>30天总销售额：" + order.total;
			addMarkLocation(order.registAddress, content);
		}
	}
}

// 创建和初始化地图函数：
function initMap() {
	createMap();// 创建地图
	setMapEvent();// 设置地图事件
	addMapControl();// 向地图添加控件
}

// 创建地图函数：
function createMap() {
	map = new BMap.Map("map");// 在百度地图容器中创建一个地图
	map.centerAndZoom("北京市", 11);
	mapIcon = new BMap.Icon('map4.png', new BMap.Size(35, 26));
	mapGeo = new BMap.Geocoder();
}

// 地图事件设置函数：
function setMapEvent() {
	map.enableDragging();// 启用地图拖拽事件，默认启用(可不写)
	map.enableScrollWheelZoom();// 启用地图滚轮放大缩小
	map.enableDoubleClickZoom();// 启用鼠标双击放大，默认启用(可不写)
	map.enableKeyboard();// 启用键盘上下左右键移动地图
}

// 地图控件添加函数：
function addMapControl() {
	// 向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_TOP_LEFT,
		type : BMAP_NAVIGATION_CONTROL_LARGE
	});
	map.addControl(ctrl_nav);
}

// 创建InfoWindow
function createInfoWindow(content) {
	return new BMap.InfoWindow(content);
}

// 在地图上添加点
function addMarkLocation(address, content) {
	mapGeo.getPoint(address, function(point) {
		if (point) {
			var marker = new BMap.Marker(point, {
				icon : mapIcon
			});
			var infowindow = createInfoWindow(content);
			marker.addEventListener("click", function() {
				this.openInfoWindow(infowindow);
			});
			map.addOverlay(marker);
		}
	});
}

// 清除地图覆盖物
function clearMap() {
	map.clearOverlays();
}