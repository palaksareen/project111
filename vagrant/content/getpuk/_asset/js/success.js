$(function() { 

			parent.window.addEventListener('load', function(){
				setIframeHeight();
			});
			
			parent.window.addEventListener('resize', function(){
				setIframeHeight();
			});
									
			function getDocHeight(){
				return Math.max(
					$(document).height(),
					$(window).height(),
					/* For opera: */
					document.documentElement.clientHeight
				);
			}
			function setIframeHeight(){
				//console.log("height: " + getDocHeight());
				window.parent.document.getElementById('pukIframe').height = 'auto';
				window.parent.document.getElementById('pukIframe').height = getDocHeight();
			}
		  
		});