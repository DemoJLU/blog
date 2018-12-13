//工具类
define([ 'jquery' ],function($) {
			var request = function(paras) {
				var url = location.href;
				var paraString = url.substring(url.indexOf("?") + 1).split("&");
				var paraObj = {};
				for (var i = 0; i < paraString.length; i++) {
					var j = paraString[i];
					paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j
							.substring(j.indexOf("=") + 1);
				}
				return paraObj[paras.toLowerCase()] || '';
			};

			var showErrMsg = function(msg) {
				if ($("#dlgWarning")[0]) {
					$("#dlgErrMsg").html(msg);
				} else {
					var dlg = '\
				<div class="modal fade" id="dlgWarning">\
					<div class="modal-dialog modal-sm">\
						<div class="modal-content">\
							<div class="modal-header">\
								<button type="button" class="close" data-dismiss="modal"\
									aria-label="Close">\
									<span aria-hidden="true">&times;</span>\
								</button>\
								<h4 class="modal-title">错误信息</h4>\
							</div>\
							<div id="dlgErrMsg" class="modal-body">'
							+ msg
							+ '</div>\
							<div class="modal-footer">\
								<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>\
							</div>\
						</div>\
					</div>\
				</div>';
					$(document.body).append(dlg);
				}
				$("#dlgWarning").modal({
					show : true
				});
			};

			var getLocalPathParams = function() {
				var url = location.search;
				var paramArr = [];
				if (url.indexOf('?') != -1) {
					var strs = url.substr(1).split('&');
					for (var i = 0; i < strs.length; i++) {
						var index = strs[i].indexOf('=');
						paramArr[strs[i].substring(0, index)] = decodeURIComponent(strs[i]
								.substr(index + 1));
					}
				}
				return paramArr;
			};

			// 将hex表示方式转换为rgb表示方式(这里返回rgb数组模式)
			function colorRgb(sColor) {
				var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
				var sColor = sColor.toLowerCase();
				if (sColor && reg.test(sColor)) {
					if (sColor.length === 4) {
						var sColorNew = "#";
						for (var i = 1; i < 4; i += 1) {
							sColorNew += sColor.slice(i, i + 1).concat(
									sColor.slice(i, i + 1));
						}
						sColor = sColorNew;
					}
					// 处理六位的颜色值
					var sColorChange = [];
					for (var i = 1; i < 7; i += 2) {
						sColorChange.push(parseInt("0x"
								+ sColor.slice(i, i + 2)));// 将16进制的数值转换为10进制
					}
					return sColorChange;
				} else {
					return sColor;
				}
			};

			// 将rgb表示方式转换为hex表示方式
			function colorHex(rgb) {
				var _this = rgb;
				var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
				if (/^(rgb|RGB)/.test(_this)) {
					var aColor = _this.replace(/(?:(|)|rgb|RGB)*/g, "").split(
							",");
					var strHex = "#";
					for (var i = 0; i < aColor.length; i++) {
						var hex = Number(aColor[i]).toString(16);
						hex = hex < 10 ? 0 + '' + hex : hex;// 保证每个rgb的值为2位
						if (hex === "0") {
							hex += hex;
						}
						strHex += hex;
					}
					if (strHex.length !== 7) {
						strHex = _this;
					}
					return strHex;
				} else if (reg.test(_this)) {
					var aNum = _this.replace(/#/, "").split("");
					if (aNum.length === 6) {
						return _this;
					} else if (aNum.length === 3) {
						var numHex = "#";
						for (var i = 0; i < aNum.length; i += 1) {
							numHex += (aNum[i] + aNum[i]);
						}
						return numHex;
					}
				} else {
					return _this;
				}
			}
			
			
			//parameter1: 起始颜色值：parameter2: 终止颜色值：parameter3:标尺格数，不能超过256：
			var getHotColorRatio = function(startColor, endColor, step) {
				if(step == null || Number(step) > 256){
					step = 256;
				}
				startRGB = colorRgb(startColor);// 转换为rgb数组模式
				startR = startRGB[0];
				startG = startRGB[1];
				startB = startRGB[2];
				endRGB = colorRgb(endColor);
				endR = endRGB[0];
				endG = endRGB[1];
				endB = endRGB[2];
				sR = (endR - startR) / step;// 总差值
				sG = (endG - startG) / step;
				sB = (endB - startB) / step;
				var colorArr = [];
				for (var i = 0; i < step; i++) {
					// 计算每一步的hex值
					var hex = colorHex('rgb('
							+ parseInt((sR * i + startR)) + ','
							+ parseInt((sG * i + startG)) + ','
							+ parseInt((sB * i + startB)) + ')');
					colorArr.push(hex);
				}
				return colorArr;
			}


			return {
				request : request,
				showErrMsg : showErrMsg,
				getLocalPathParams : getLocalPathParams,
				getHotColorRatio :getHotColorRatio
			};

		});