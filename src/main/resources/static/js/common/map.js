var Map = function (){
	  /************基础变量**************/
	  var hashmap = {};
	  var keys = [];
	  var vals = [];
	  var entrys = [];
	  var size = 0;
	  var index = {};
	  
	  var Entry = function(key,value){
	    var entryKey = key;
	    var entryValue = value;
	    this.getKey = function (){
	      return entryKey;
	    };
	    this.getValue = function(){
	      return entryValue;
	    };
	  };
	  /************基本方法 按字母排序**************/
	  this.clear = function(key) {
	    hashmap[key] = undefined;
	    var i = index[key];
	    entrys.splice(i,1);
	    vals.splice(i,1);
	    keys.splice(i,1);
	    size --;
	  };
	    
	  this.entrySet = function() {
	    return entrys;
	  };
	    
	  this.get = function(key){
	    return hashmap[key];
	  };
	    
	  this.isEmpty = function() {
	    if(hashmap) return true;
	    return false;
	  };
	  
	  this.keySet = function() {
	    return keys;
	  };
	    
	  this.put = function(key,value){
	    if(!this.get(key)){
	      entrys.push(new Entry(key,value));
	      keys.push(key);
	      vals.push(value);
	      index[key] = size;
	      size ++;
	    } else {
	      var i = index[key];
	      entrys[i] = new Entry(key,value);
	      vals[i] = value;
	    }
	    hashmap[key] = value;
	  };
	  
	  this.size = function() {
	    return size;
	  };
	    
	  this.values = function() {
	    return vals;
	  };
	};
	/************扩展方法**************/
	Map.prototype = {
	  containsKey : function(key) {
	    if(this.get(key)) return true;
	    return false;
	  },
	  putAll : function(set) {
	    for(var e in set){
	      if(set[e]){
	        this.put(e,set[e]);
	      }
	    }
	  },
	  remove : function(key) {
	    var v = this.get(key);
	    this.clear(key);
	    return v;
	  }
	};
	
//	var h = new Map();
//	h.put('a',10);
//	h.put('b',11);
//	h.put('c',3);
//	h.put('d',5);
//	console.info(h.size());
//	h.clear('a');
//	console.info(h.containsKey('a'));
//	console.info(h.containsKey('b'));
//	console.info(h.size());
//	  
//	console.log(h.entrySet());
//	console.log(h.keySet());
//	console.log(h.values());
//	  
//	for(var i in h.entrySet()){
//	  var obj = h.entrySet()[i];
//	  console.log(obj.getKey() + ":" + obj.getValue());
//	}