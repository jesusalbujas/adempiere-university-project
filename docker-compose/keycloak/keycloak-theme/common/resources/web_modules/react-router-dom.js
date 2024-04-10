import t from"./react.js";import{P as e}from"./common/index-76ce4e6e.js";var n=function(){};function r(){return(r=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t}).apply(this,arguments)}function o(t){return"/"===t.charAt(0)}function i(t,e){for(var n=e,r=n+1,o=t.length;r<o;n+=1,r+=1)t[n]=t[r];t.pop()}function a(t,e){void 0===e&&(e="");var n,r=t&&t.split("/")||[],a=e&&e.split("/")||[],c=t&&o(t),s=e&&o(e),u=c||s;if(t&&o(t)?a=r:r.length&&(a.pop(),a=a.concat(r)),!a.length)return"/";if(a.length){var p=a[a.length-1];n="."===p||".."===p||""===p}else n=!1;for(var l=0,f=a.length;f>=0;f--){var h=a[f];"."===h?i(a,f):".."===h?(i(a,f),l++):l&&(i(a,f),l--)}if(!u)for(;l--;l)a.unshift("..");!u||""===a[0]||a[0]&&o(a[0])||a.unshift("");var y=a.join("/");return n&&"/"!==y.substr(-1)&&(y+="/"),y}function c(t){return t.valueOf?t.valueOf():Object.prototype.valueOf.call(t)}var s="Invariant failed";function u(t,e){if(!t)throw new Error(s)}function p(t){return"/"===t.charAt(0)?t:"/"+t}function l(t){return"/"===t.charAt(0)?t.substr(1):t}function f(t,e){return function(t,e){return 0===t.toLowerCase().indexOf(e.toLowerCase())&&-1!=="/?#".indexOf(t.charAt(e.length))}(t,e)?t.substr(e.length):t}function h(t){return"/"===t.charAt(t.length-1)?t.slice(0,-1):t}function y(t){var e=t.pathname,n=t.search,r=t.hash,o=e||"/";return n&&"?"!==n&&(o+="?"===n.charAt(0)?n:"?"+n),r&&"#"!==r&&(o+="#"===r.charAt(0)?r:"#"+r),o}function d(t,e,n,o){var i;"string"==typeof t?(i=function(t){var e=t||"/",n="",r="",o=e.indexOf("#");-1!==o&&(r=e.substr(o),e=e.substr(0,o));var i=e.indexOf("?");return-1!==i&&(n=e.substr(i),e=e.substr(0,i)),{pathname:e,search:"?"===n?"":n,hash:"#"===r?"":r}}(t)).state=e:(void 0===(i=r({},t)).pathname&&(i.pathname=""),i.search?"?"!==i.search.charAt(0)&&(i.search="?"+i.search):i.search="",i.hash?"#"!==i.hash.charAt(0)&&(i.hash="#"+i.hash):i.hash="",void 0!==e&&void 0===i.state&&(i.state=e));try{i.pathname=decodeURI(i.pathname)}catch(t){throw t instanceof URIError?new URIError('Pathname "'+i.pathname+'" could not be decoded. This is likely caused by an invalid percent-encoding.'):t}return n&&(i.key=n),o?i.pathname?"/"!==i.pathname.charAt(0)&&(i.pathname=a(i.pathname,o.pathname)):i.pathname=o.pathname:i.pathname||(i.pathname="/"),i}function v(){var t=null;var e=[];return{setPrompt:function(e){return t=e,function(){t===e&&(t=null)}},confirmTransitionTo:function(e,n,r,o){if(null!=t){var i="function"==typeof t?t(e,n):t;"string"==typeof i?"function"==typeof r?r(i,o):o(!0):o(!1!==i)}else o(!0)},appendListener:function(t){var n=!0;function r(){n&&t.apply(void 0,arguments)}return e.push(r),function(){n=!1,e=e.filter((function(t){return t!==r}))}},notifyListeners:function(){for(var t=arguments.length,n=new Array(t),r=0;r<t;r++)n[r]=arguments[r];e.forEach((function(t){return t.apply(void 0,n)}))}}}var m=!("undefined"==typeof window||!window.document||!window.document.createElement);function b(t,e){e(window.confirm(t))}function g(){try{return window.history.state||{}}catch(t){return{}}}function w(t){void 0===t&&(t={}),m||u(!1);var e,n=window.history,o=(-1===(e=window.navigator.userAgent).indexOf("Android 2.")&&-1===e.indexOf("Android 4.0")||-1===e.indexOf("Mobile Safari")||-1!==e.indexOf("Chrome")||-1!==e.indexOf("Windows Phone"))&&window.history&&"pushState"in window.history,i=!(-1===window.navigator.userAgent.indexOf("Trident")),a=t,c=a.forceRefresh,s=void 0!==c&&c,l=a.getUserConfirmation,w=void 0===l?b:l,O=a.keyLength,x=void 0===O?6:O,P=t.basename?h(p(t.basename)):"";function j(t){var e=t||{},n=e.key,r=e.state,o=window.location,i=o.pathname+o.search+o.hash;return P&&(i=f(i,P)),d(i,r,n)}function E(){return Math.random().toString(36).substr(2,x)}var R=v();function T(t){r(I,t),I.length=n.length,R.notifyListeners(I.location,I.action)}function C(t){(function(t){return void 0===t.state&&-1===navigator.userAgent.indexOf("CriOS")})(t)||S(j(t.state))}function k(){S(j(g()))}var A=!1;function S(t){if(A)A=!1,T();else{R.confirmTransitionTo(t,"POP",w,(function(e){e?T({action:"POP",location:t}):function(t){var e=I.location,n=L.indexOf(e.key);-1===n&&(n=0);var r=L.indexOf(t.key);-1===r&&(r=0);var o=n-r;o&&(A=!0,U(o))}(t)}))}}var _=j(g()),L=[_.key];function M(t){return P+y(t)}function U(t){n.go(t)}var q=0;function W(t){1===(q+=t)&&1===t?(window.addEventListener("popstate",C),i&&window.addEventListener("hashchange",k)):0===q&&(window.removeEventListener("popstate",C),i&&window.removeEventListener("hashchange",k))}var H=!1;var I={length:n.length,action:"POP",location:_,createHref:M,push:function(t,e){var r=d(t,e,E(),I.location);R.confirmTransitionTo(r,"PUSH",w,(function(t){if(t){var e=M(r),i=r.key,a=r.state;if(o)if(n.pushState({key:i,state:a},null,e),s)window.location.href=e;else{var c=L.indexOf(I.location.key),u=L.slice(0,c+1);u.push(r.key),L=u,T({action:"PUSH",location:r})}else window.location.href=e}}))},replace:function(t,e){var r=d(t,e,E(),I.location);R.confirmTransitionTo(r,"REPLACE",w,(function(t){if(t){var e=M(r),i=r.key,a=r.state;if(o)if(n.replaceState({key:i,state:a},null,e),s)window.location.replace(e);else{var c=L.indexOf(I.location.key);-1!==c&&(L[c]=r.key),T({action:"REPLACE",location:r})}else window.location.replace(e)}}))},go:U,goBack:function(){U(-1)},goForward:function(){U(1)},block:function(t){void 0===t&&(t=!1);var e=R.setPrompt(t);return H||(W(1),H=!0),function(){return H&&(H=!1,W(-1)),e()}},listen:function(t){var e=R.appendListener(t);return W(1),function(){W(-1),e()}}};return I}var O={hashbang:{encodePath:function(t){return"!"===t.charAt(0)?t:"!/"+l(t)},decodePath:function(t){return"!"===t.charAt(0)?t.substr(1):t}},noslash:{encodePath:l,decodePath:p},slash:{encodePath:p,decodePath:p}};function x(t){var e=t.indexOf("#");return-1===e?t:t.slice(0,e)}function P(){var t=window.location.href,e=t.indexOf("#");return-1===e?"":t.substring(e+1)}function j(t){window.location.replace(x(window.location.href)+"#"+t)}function E(t){void 0===t&&(t={}),m||u(!1);var e=window.history;window.navigator.userAgent.indexOf("Firefox");var n=t,o=n.getUserConfirmation,i=void 0===o?b:o,a=n.hashType,c=void 0===a?"slash":a,s=t.basename?h(p(t.basename)):"",l=O[c],g=l.encodePath,w=l.decodePath;function E(){var t=w(P());return s&&(t=f(t,s)),d(t)}var R=v();function T(t){r(I,t),I.length=e.length,R.notifyListeners(I.location,I.action)}var C=!1,k=null;function A(){var t,e,n=P(),r=g(n);if(n!==r)j(r);else{var o=E(),a=I.location;if(!C&&(e=o,(t=a).pathname===e.pathname&&t.search===e.search&&t.hash===e.hash))return;if(k===y(o))return;k=null,function(t){if(C)C=!1,T();else{R.confirmTransitionTo(t,"POP",i,(function(e){e?T({action:"POP",location:t}):function(t){var e=I.location,n=M.lastIndexOf(y(e));-1===n&&(n=0);var r=M.lastIndexOf(y(t));-1===r&&(r=0);var o=n-r;o&&(C=!0,U(o))}(t)}))}}(o)}}var S=P(),_=g(S);S!==_&&j(_);var L=E(),M=[y(L)];function U(t){e.go(t)}var q=0;function W(t){1===(q+=t)&&1===t?window.addEventListener("hashchange",A):0===q&&window.removeEventListener("hashchange",A)}var H=!1;var I={length:e.length,action:"POP",location:L,createHref:function(t){var e=document.querySelector("base"),n="";return e&&e.getAttribute("href")&&(n=x(window.location.href)),n+"#"+g(s+y(t))},push:function(t,e){var n=d(t,void 0,void 0,I.location);R.confirmTransitionTo(n,"PUSH",i,(function(t){if(t){var e=y(n),r=g(s+e);if(P()!==r){k=e,function(t){window.location.hash=t}(r);var o=M.lastIndexOf(y(I.location)),i=M.slice(0,o+1);i.push(e),M=i,T({action:"PUSH",location:n})}else T()}}))},replace:function(t,e){var n=d(t,void 0,void 0,I.location);R.confirmTransitionTo(n,"REPLACE",i,(function(t){if(t){var e=y(n),r=g(s+e);P()!==r&&(k=e,j(r));var o=M.indexOf(y(I.location));-1!==o&&(M[o]=e),T({action:"REPLACE",location:n})}}))},go:U,goBack:function(){U(-1)},goForward:function(){U(1)},block:function(t){void 0===t&&(t=!1);var e=R.setPrompt(t);return H||(W(1),H=!0),function(){return H&&(H=!1,W(-1)),e()}},listen:function(t){var e=R.appendListener(t);return W(1),function(){W(-1),e()}}};return I}var R=function(t,e,n,r,o,i,a,c){if(!t){var s;if(void 0===e)s=new Error("Minified exception occurred; use the non-minified dev environment for the full error message and additional helpful warnings.");else{var u=[n,r,o,i,a,c],p=0;(s=new Error(e.replace(/%s/g,(function(){return u[p++]})))).name="Invariant Violation"}throw s.framesToPop=1,s}},T=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t};function C(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function k(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var A=function(e){function r(){var t,n;C(this,r);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=n=k(this,e.call.apply(e,[this].concat(i))),n.state={match:n.computeMatch(n.props.history.location.pathname)},k(n,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.getChildContext=function(){return{router:T({},this.context.router,{history:this.props.history,route:{location:this.props.history.location,match:this.state.match}})}},r.prototype.computeMatch=function(t){return{path:"/",url:"/",params:{},isExact:"/"===t}},r.prototype.componentWillMount=function(){var e=this,n=this.props,r=n.children,o=n.history;R(null==r||1===t.Children.count(r),"A <Router> may have only one child element"),this.unlisten=o.listen((function(){e.setState({match:e.computeMatch(o.location.pathname)})}))},r.prototype.componentWillReceiveProps=function(t){n(this.props.history===t.history)},r.prototype.componentWillUnmount=function(){this.unlisten()},r.prototype.render=function(){var e=this.props.children;return e?t.Children.only(e):null},r}(t.Component);A.propTypes={history:e.object.isRequired,children:e.node},A.contextTypes={router:e.object},A.childContextTypes={router:e.object.isRequired};var S=A;function _(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function L(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var M=function(e){function r(){var t,n;_(this,r);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=n=L(this,e.call.apply(e,[this].concat(i))),n.history=w(n.props),L(n,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.componentWillMount=function(){n(!this.props.history)},r.prototype.render=function(){return t.createElement(S,{history:this.history,children:this.props.children})},r}(t.Component);M.propTypes={basename:e.string,forceRefresh:e.bool,getUserConfirmation:e.func,keyLength:e.number,children:e.node};var U=M;function q(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function W(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var H=function(e){function r(){var t,n;q(this,r);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=n=W(this,e.call.apply(e,[this].concat(i))),n.history=E(n.props),W(n,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.componentWillMount=function(){n(!this.props.history)},r.prototype.render=function(){return t.createElement(S,{history:this.history,children:this.props.children})},r}(t.Component);H.propTypes={basename:e.string,getUserConfirmation:e.func,hashType:e.oneOf(["hashbang","noslash","slash"]),children:e.node};var I=H,N=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t};function $(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function B(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var F=function(t){return!!(t.metaKey||t.altKey||t.ctrlKey||t.shiftKey)},Y=function(e){function n(){var t,r;$(this,n);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=r=B(this,e.call.apply(e,[this].concat(i))),r.handleClick=function(t){if(r.props.onClick&&r.props.onClick(t),!t.defaultPrevented&&0===t.button&&!r.props.target&&!F(t)){t.preventDefault();var e=r.context.router.history,n=r.props,o=n.replace,i=n.to;o?e.replace(i):e.push(i)}},B(r,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(n,e),n.prototype.render=function(){var e=this.props;e.replace;var n=e.to,r=e.innerRef,o=function(t,e){var n={};for(var r in t)e.indexOf(r)>=0||Object.prototype.hasOwnProperty.call(t,r)&&(n[r]=t[r]);return n}(e,["replace","to","innerRef"]);R(this.context.router,"You should not use <Link> outside a <Router>"),R(void 0!==n,'You must specify the "to" property');var i=this.context.router.history,a="string"==typeof n?d(n,null,null,i.location):n,c=i.createHref(a);return t.createElement("a",N({},o,{onClick:this.handleClick,href:c,ref:r}))},n}(t.Component);Y.propTypes={onClick:e.func,target:e.string,replace:e.bool,to:e.oneOfType([e.string,e.object]).isRequired,innerRef:e.oneOfType([e.string,e.func])},Y.defaultProps={replace:!1},Y.contextTypes={router:e.shape({history:e.shape({push:e.func.isRequired,replace:e.func.isRequired,createHref:e.func.isRequired}).isRequired}).isRequired};var D=Y;function K(t){var e=t.pathname,n=t.search,r=t.hash,o=e||"/";return n&&"?"!==n&&(o+="?"===n.charAt(0)?n:"?"+n),r&&"#"!==r&&(o+="#"===r.charAt(0)?r:"#"+r),o}function J(t,e,n,o){var i;"string"==typeof t?(i=function(t){var e=t||"/",n="",r="",o=e.indexOf("#");-1!==o&&(r=e.substr(o),e=e.substr(0,o));var i=e.indexOf("?");return-1!==i&&(n=e.substr(i),e=e.substr(0,i)),{pathname:e,search:"?"===n?"":n,hash:"#"===r?"":r}}(t)).state=e:(void 0===(i=r({},t)).pathname&&(i.pathname=""),i.search?"?"!==i.search.charAt(0)&&(i.search="?"+i.search):i.search="",i.hash?"#"!==i.hash.charAt(0)&&(i.hash="#"+i.hash):i.hash="",void 0!==e&&void 0===i.state&&(i.state=e));try{i.pathname=decodeURI(i.pathname)}catch(t){throw t instanceof URIError?new URIError('Pathname "'+i.pathname+'" could not be decoded. This is likely caused by an invalid percent-encoding.'):t}return n&&(i.key=n),o?i.pathname?"/"!==i.pathname.charAt(0)&&(i.pathname=a(i.pathname,o.pathname)):i.pathname=o.pathname:i.pathname||(i.pathname="/"),i}function V(t,e){return t.pathname===e.pathname&&t.search===e.search&&t.hash===e.hash&&t.key===e.key&&function t(e,n){if(e===n)return!0;if(null==e||null==n)return!1;if(Array.isArray(e))return Array.isArray(n)&&e.length===n.length&&e.every((function(e,r){return t(e,n[r])}));if("object"==typeof e||"object"==typeof n){var r=c(e),o=c(n);return r!==e||o!==n?t(r,o):Object.keys(Object.assign({},e,n)).every((function(r){return t(e[r],n[r])}))}return!1}(t.state,e.state)}function G(t,e,n){return Math.min(Math.max(t,e),n)}function z(t){void 0===t&&(t={});var e,n,o=t,i=o.getUserConfirmation,a=o.initialEntries,c=void 0===a?["/"]:a,s=o.initialIndex,u=void 0===s?0:s,p=o.keyLength,l=void 0===p?6:p,f=(e=null,n=[],{setPrompt:function(t){return e=t,function(){e===t&&(e=null)}},confirmTransitionTo:function(t,n,r,o){if(null!=e){var i="function"==typeof e?e(t,n):e;"string"==typeof i?"function"==typeof r?r(i,o):o(!0):o(!1!==i)}else o(!0)},appendListener:function(t){var e=!0;function r(){e&&t.apply(void 0,arguments)}return n.push(r),function(){e=!1,n=n.filter((function(t){return t!==r}))}},notifyListeners:function(){for(var t=arguments.length,e=new Array(t),r=0;r<t;r++)e[r]=arguments[r];n.forEach((function(t){return t.apply(void 0,e)}))}});function h(t){r(g,t),g.length=g.entries.length,f.notifyListeners(g.location,g.action)}function y(){return Math.random().toString(36).substr(2,l)}var d=G(u,0,c.length-1),v=c.map((function(t){return J(t,void 0,"string"==typeof t?y():t.key||y())})),m=K;function b(t){var e=G(g.index+t,0,g.entries.length-1),n=g.entries[e];f.confirmTransitionTo(n,"POP",i,(function(t){t?h({action:"POP",location:n,index:e}):h()}))}var g={length:v.length,action:"POP",location:v[d],index:d,entries:v,createHref:m,push:function(t,e){var n=J(t,e,y(),g.location);f.confirmTransitionTo(n,"PUSH",i,(function(t){if(t){var e=g.index+1,r=g.entries.slice(0);r.length>e?r.splice(e,r.length-e,n):r.push(n),h({action:"PUSH",location:n,index:e,entries:r})}}))},replace:function(t,e){var n=J(t,e,y(),g.location);f.confirmTransitionTo(n,"REPLACE",i,(function(t){t&&(g.entries[g.index]=n,h({action:"REPLACE",location:n}))}))},go:b,goBack:function(){b(-1)},goForward:function(){b(1)},canGo:function(t){var e=g.index+t;return e>=0&&e<g.entries.length},block:function(t){return void 0===t&&(t=!1),f.setPrompt(t)},listen:function(t){return f.appendListener(t)}};return g}function Q(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function X(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var Z=function(e){function r(){var t,n;Q(this,r);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=n=X(this,e.call.apply(e,[this].concat(i))),n.history=z(n.props),X(n,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.componentWillMount=function(){n(!this.props.history)},r.prototype.render=function(){return t.createElement(S,{history:this.history,children:this.props.children})},r}(t.Component);Z.propTypes={initialEntries:e.array,initialIndex:e.number,getUserConfirmation:e.func,keyLength:e.number,children:e.node};var tt=Z,et=Array.isArray||function(t){return"[object Array]"==Object.prototype.toString.call(t)},nt=vt,rt=st,ot=function(t,e){return pt(st(t,e),e)},it=pt,at=dt,ct=new RegExp(["(\\\\.)","([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))"].join("|"),"g");function st(t,e){for(var n,r=[],o=0,i=0,a="",c=e&&e.delimiter||"/";null!=(n=ct.exec(t));){var s=n[0],u=n[1],p=n.index;if(a+=t.slice(i,p),i=p+s.length,u)a+=u[1];else{var l=t[i],f=n[2],h=n[3],y=n[4],d=n[5],v=n[6],m=n[7];a&&(r.push(a),a="");var b=null!=f&&null!=l&&l!==f,g="+"===v||"*"===v,w="?"===v||"*"===v,O=n[2]||c,x=y||d;r.push({name:h||o++,prefix:f||"",delimiter:O,optional:w,repeat:g,partial:b,asterisk:!!m,pattern:x?ft(x):m?".*":"[^"+lt(O)+"]+?"})}}return i<t.length&&(a+=t.substr(i)),a&&r.push(a),r}function ut(t){return encodeURI(t).replace(/[\/?#]/g,(function(t){return"%"+t.charCodeAt(0).toString(16).toUpperCase()}))}function pt(t,e){for(var n=new Array(t.length),r=0;r<t.length;r++)"object"==typeof t[r]&&(n[r]=new RegExp("^(?:"+t[r].pattern+")$",yt(e)));return function(e,r){for(var o="",i=e||{},a=(r||{}).pretty?ut:encodeURIComponent,c=0;c<t.length;c++){var s=t[c];if("string"!=typeof s){var u,p=i[s.name];if(null==p){if(s.optional){s.partial&&(o+=s.prefix);continue}throw new TypeError('Expected "'+s.name+'" to be defined')}if(et(p)){if(!s.repeat)throw new TypeError('Expected "'+s.name+'" to not repeat, but received `'+JSON.stringify(p)+"`");if(0===p.length){if(s.optional)continue;throw new TypeError('Expected "'+s.name+'" to not be empty')}for(var l=0;l<p.length;l++){if(u=a(p[l]),!n[c].test(u))throw new TypeError('Expected all "'+s.name+'" to match "'+s.pattern+'", but received `'+JSON.stringify(u)+"`");o+=(0===l?s.prefix:s.delimiter)+u}}else{if(u=s.asterisk?encodeURI(p).replace(/[?#]/g,(function(t){return"%"+t.charCodeAt(0).toString(16).toUpperCase()})):a(p),!n[c].test(u))throw new TypeError('Expected "'+s.name+'" to match "'+s.pattern+'", but received "'+u+'"');o+=s.prefix+u}}else o+=s}return o}}function lt(t){return t.replace(/([.+*?=^!:${}()[\]|\/\\])/g,"\\$1")}function ft(t){return t.replace(/([=!:$\/()])/g,"\\$1")}function ht(t,e){return t.keys=e,t}function yt(t){return t&&t.sensitive?"":"i"}function dt(t,e,n){et(e)||(n=e||n,e=[]);for(var r=(n=n||{}).strict,o=!1!==n.end,i="",a=0;a<t.length;a++){var c=t[a];if("string"==typeof c)i+=lt(c);else{var s=lt(c.prefix),u="(?:"+c.pattern+")";e.push(c),c.repeat&&(u+="(?:"+s+u+")*"),i+=u=c.optional?c.partial?s+"("+u+")?":"(?:"+s+"("+u+"))?":s+"("+u+")"}}var p=lt(n.delimiter||"/"),l=i.slice(-p.length)===p;return r||(i=(l?i.slice(0,-p.length):i)+"(?:"+p+"(?=$))?"),i+=o?"$":r&&l?"":"(?="+p+"|$)",ht(new RegExp("^"+i,yt(n)),e)}function vt(t,e,n){return et(e)||(n=e||n,e=[]),n=n||{},t instanceof RegExp?function(t,e){var n=t.source.match(/\((?!\?)/g);if(n)for(var r=0;r<n.length;r++)e.push({name:r,prefix:null,delimiter:null,optional:!1,repeat:!1,partial:!1,asterisk:!1,pattern:null});return ht(t,e)}(t,e):et(t)?function(t,e,n){for(var r=[],o=0;o<t.length;o++)r.push(vt(t[o],e,n).source);return ht(new RegExp("(?:"+r.join("|")+")",yt(n)),e)}(t,e,n):function(t,e,n){return dt(st(t,n),e,n)}(t,e,n)}nt.parse=rt,nt.compile=ot,nt.tokensToFunction=it,nt.tokensToRegExp=at;var mt={},bt=0,gt=function(t,e){var n=""+e.end+e.strict+e.sensitive,r=mt[n]||(mt[n]={});if(r[t])return r[t];var o=[],i={re:nt(t,o,e),keys:o};return bt<1e4&&(r[t]=i,bt++),i},wt=function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},n=arguments[2];"string"==typeof e&&(e={path:e});var r=e,o=r.path,i=r.exact,a=void 0!==i&&i,c=r.strict,s=void 0!==c&&c,u=r.sensitive,p=void 0!==u&&u;if(null==o)return n;var l=gt(o,{end:a,strict:s,sensitive:p}),f=l.re,h=l.keys,y=f.exec(t);if(!y)return null;var d=y[0],v=y.slice(1),m=t===d;return a&&!m?null:{path:o,url:"/"===o&&""===d?"/":d,isExact:m,params:h.reduce((function(t,e,n){return t[e.name]=v[n],t}),{})}},Ot=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t};function xt(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function Pt(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var jt=function(e){return 0===t.Children.count(e)},Et=function(e){function r(){var t,n;xt(this,r);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=n=Pt(this,e.call.apply(e,[this].concat(i))),n.state={match:n.computeMatch(n.props,n.context.router)},Pt(n,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.getChildContext=function(){return{router:Ot({},this.context.router,{route:{location:this.props.location||this.context.router.route.location,match:this.state.match}})}},r.prototype.computeMatch=function(t,e){var n=t.computedMatch,r=t.location,o=t.path,i=t.strict,a=t.exact,c=t.sensitive;if(n)return n;R(e,"You should not use <Route> or withRouter() outside a <Router>");var s=e.route,u=(r||s.location).pathname;return wt(u,{path:o,strict:i,exact:a,sensitive:c},s.match)},r.prototype.componentWillMount=function(){n(!(this.props.component&&this.props.render)),n(!(this.props.component&&this.props.children&&!jt(this.props.children))),n(!(this.props.render&&this.props.children&&!jt(this.props.children)))},r.prototype.componentWillReceiveProps=function(t,e){n(!(t.location&&!this.props.location)),n(!(!t.location&&this.props.location)),this.setState({match:this.computeMatch(t,e.router)})},r.prototype.render=function(){var e=this.state.match,n=this.props,r=n.children,o=n.component,i=n.render,a=this.context.router,c=a.history,s=a.route,u=a.staticContext,p={match:e,location:this.props.location||s.location,history:c,staticContext:u};return o?e?t.createElement(o,p):null:i?e?i(p):null:"function"==typeof r?r(p):r&&!jt(r)?t.Children.only(r):null},r}(t.Component);Et.propTypes={computedMatch:e.object,path:e.string,exact:e.bool,strict:e.bool,sensitive:e.bool,component:e.func,render:e.func,children:e.oneOfType([e.func,e.node]),location:e.object},Et.contextTypes={router:e.shape({history:e.object.isRequired,route:e.object.isRequired,staticContext:e.object})},Et.childContextTypes={router:e.object.isRequired};var Rt=Et,Tt=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t},Ct="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t};var kt=function(e){var n=e.to,r=e.exact,o=e.strict,i=e.location,a=e.activeClassName,c=e.className,s=e.activeStyle,u=e.style,p=e.isActive,l=e["aria-current"],f=function(t,e){var n={};for(var r in t)e.indexOf(r)>=0||Object.prototype.hasOwnProperty.call(t,r)&&(n[r]=t[r]);return n}(e,["to","exact","strict","location","activeClassName","className","activeStyle","style","isActive","aria-current"]),h="object"===(void 0===n?"undefined":Ct(n))?n.pathname:n,y=h&&h.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1");return t.createElement(Rt,{path:y,exact:r,strict:o,location:i,children:function(e){var r=e.location,o=e.match,i=!!(p?p(o,r):o);return t.createElement(D,Tt({to:n,className:i?[c,a].filter((function(t){return t})).join(" "):c,style:i?Tt({},u,s):u,"aria-current":i&&l||null},f))}})};kt.propTypes={to:D.propTypes.to,exact:e.bool,strict:e.bool,location:e.object,activeClassName:e.string,className:e.string,activeStyle:e.object,style:e.object,isActive:e.func,"aria-current":e.oneOf(["page","step","location","date","time","true"])},kt.defaultProps={activeClassName:"active","aria-current":"page"};var At=kt;function St(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function _t(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var Lt=function(t){function e(){return St(this,e),_t(this,t.apply(this,arguments))}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(e,t),e.prototype.enable=function(t){this.unblock&&this.unblock(),this.unblock=this.context.router.history.block(t)},e.prototype.disable=function(){this.unblock&&(this.unblock(),this.unblock=null)},e.prototype.componentWillMount=function(){R(this.context.router,"You should not use <Prompt> outside a <Router>"),this.props.when&&this.enable(this.props.message)},e.prototype.componentWillReceiveProps=function(t){t.when?this.props.when&&this.props.message===t.message||this.enable(t.message):this.disable()},e.prototype.componentWillUnmount=function(){this.disable()},e.prototype.render=function(){return null},e}(t.Component);Lt.propTypes={when:e.bool,message:e.oneOfType([e.func,e.string]).isRequired},Lt.defaultProps={when:!0},Lt.contextTypes={router:e.shape({history:e.shape({block:e.func.isRequired}).isRequired}).isRequired};var Mt=Lt,Ut={},qt=0,Wt=function(t){var e=t,n=Ut[e]||(Ut[e]={});if(n[t])return n[t];var r=nt.compile(t);return qt<1e4&&(n[t]=r,qt++),r},Ht=function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"/",e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};if("/"===t)return t;var n=Wt(t);return n(e,{pretty:!0})},It=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t};function Nt(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function $t(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var Bt=function(t){function e(){return Nt(this,e),$t(this,t.apply(this,arguments))}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(e,t),e.prototype.isStatic=function(){return this.context.router&&this.context.router.staticContext},e.prototype.componentWillMount=function(){R(this.context.router,"You should not use <Redirect> outside a <Router>"),this.isStatic()&&this.perform()},e.prototype.componentDidMount=function(){this.isStatic()||this.perform()},e.prototype.componentDidUpdate=function(t){var e=J(t.to),r=J(this.props.to);V(e,r)?n(!1,"You tried to redirect to the same route you're currently on: \""+r.pathname+r.search+'"'):this.perform()},e.prototype.computeTo=function(t){var e=t.computedMatch,n=t.to;return e?"string"==typeof n?Ht(n,e.params):It({},n,{pathname:Ht(n.pathname,e.params)}):n},e.prototype.perform=function(){var t=this.context.router.history,e=this.props.push,n=this.computeTo(this.props);e?t.push(n):t.replace(n)},e.prototype.render=function(){return null},e}(t.Component);Bt.propTypes={computedMatch:e.object,push:e.bool,from:e.string,to:e.oneOfType([e.string,e.object]).isRequired},Bt.defaultProps={push:!1},Bt.contextTypes={router:e.shape({history:e.shape({push:e.func.isRequired,replace:e.func.isRequired}).isRequired,staticContext:e.object}).isRequired};var Ft=Bt,Yt=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t};function Dt(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function Kt(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var Jt=function(t){return"/"===t.charAt(0)?t:"/"+t},Vt=function(t,e){return t?Yt({},e,{pathname:Jt(t)+e.pathname}):e},Gt=function(t,e){if(!t)return e;var n=Jt(t);return 0!==e.pathname.indexOf(n)?e:Yt({},e,{pathname:e.pathname.substr(n.length)})},zt=function(t){return"string"==typeof t?t:K(t)},Qt=function(t){return function(){R(!1,"You cannot %s with <StaticRouter>",t)}},Xt=function(){},Zt=function(e){function r(){var t,n;Dt(this,r);for(var o=arguments.length,i=Array(o),a=0;a<o;a++)i[a]=arguments[a];return t=n=Kt(this,e.call.apply(e,[this].concat(i))),n.createHref=function(t){return Jt(n.props.basename+zt(t))},n.handlePush=function(t){var e=n.props,r=e.basename,o=e.context;o.action="PUSH",o.location=Vt(r,J(t)),o.url=zt(o.location)},n.handleReplace=function(t){var e=n.props,r=e.basename,o=e.context;o.action="REPLACE",o.location=Vt(r,J(t)),o.url=zt(o.location)},n.handleListen=function(){return Xt},n.handleBlock=function(){return Xt},Kt(n,t)}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.getChildContext=function(){return{router:{staticContext:this.props.context}}},r.prototype.componentWillMount=function(){n(!this.props.history)},r.prototype.render=function(){var e=this.props,n=e.basename;e.context;var r=e.location,o=function(t,e){var n={};for(var r in t)e.indexOf(r)>=0||Object.prototype.hasOwnProperty.call(t,r)&&(n[r]=t[r]);return n}(e,["basename","context","location"]),i={createHref:this.createHref,action:"POP",location:Gt(n,J(r)),push:this.handlePush,replace:this.handleReplace,go:Qt("go"),goBack:Qt("goBack"),goForward:Qt("goForward"),listen:this.handleListen,block:this.handleBlock};return t.createElement(S,Yt({},o,{history:i}))},r}(t.Component);Zt.propTypes={basename:e.string,context:e.object.isRequired,location:e.oneOfType([e.string,e.object])},Zt.defaultProps={basename:"",location:"/"},Zt.childContextTypes={router:e.object.isRequired};var te=Zt;function ee(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function ne(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}var re=function(e){function r(){return ee(this,r),ne(this,e.apply(this,arguments))}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(r,e),r.prototype.componentWillMount=function(){R(this.context.router,"You should not use <Switch> outside a <Router>")},r.prototype.componentWillReceiveProps=function(t){n(!(t.location&&!this.props.location)),n(!(!t.location&&this.props.location))},r.prototype.render=function(){var e=this.context.router.route,n=this.props.children,r=this.props.location||e.location,o=void 0,i=void 0;return t.Children.forEach(n,(function(n){if(null==o&&t.isValidElement(n)){var a=n.props,c=a.path,s=a.exact,u=a.strict,p=a.sensitive,l=a.from,f=c||l;i=n,o=wt(r.pathname,{path:f,exact:s,strict:u,sensitive:p},e.match)}})),o?t.cloneElement(i,{location:r,computedMatch:o}):null},r}(t.Component);re.contextTypes={router:e.shape({route:e.object.isRequired}).isRequired},re.propTypes={children:e.node,location:e.object};var oe=re,ie={childContextTypes:!0,contextTypes:!0,defaultProps:!0,displayName:!0,getDefaultProps:!0,getDerivedStateFromProps:!0,mixins:!0,propTypes:!0,type:!0},ae={name:!0,length:!0,prototype:!0,caller:!0,callee:!0,arguments:!0,arity:!0},ce=Object.defineProperty,se=Object.getOwnPropertyNames,ue=Object.getOwnPropertySymbols,pe=Object.getOwnPropertyDescriptor,le=Object.getPrototypeOf,fe=le&&le(Object);var he=function t(e,n,r){if("string"!=typeof n){if(fe){var o=le(n);o&&o!==fe&&t(e,o,r)}var i=se(n);ue&&(i=i.concat(ue(n)));for(var a=0;a<i.length;++a){var c=i[a];if(!(ie[c]||ae[c]||r&&r[c])){var s=pe(n,c);try{ce(e,c,s)}catch(t){}}}return e}return e},ye=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t};var de=function(n){var r=function(e){var r=e.wrappedComponentRef,o=function(t,e){var n={};for(var r in t)e.indexOf(r)>=0||Object.prototype.hasOwnProperty.call(t,r)&&(n[r]=t[r]);return n}(e,["wrappedComponentRef"]);return t.createElement(Rt,{children:function(e){return t.createElement(n,ye({},o,e,{ref:r}))}})};return r.displayName="withRouter("+(n.displayName||n.name)+")",r.WrappedComponent=n,r.propTypes={wrappedComponentRef:e.func},he(r,n)};export{U as BrowserRouter,I as HashRouter,D as Link,tt as MemoryRouter,At as NavLink,Mt as Prompt,Ft as Redirect,Rt as Route,S as Router,te as StaticRouter,oe as Switch,Ht as generatePath,wt as matchPath,de as withRouter};
//# sourceMappingURL=react-router-dom.js.map
