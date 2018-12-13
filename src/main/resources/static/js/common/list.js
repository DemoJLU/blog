//兼容IE8-，为Array原型添加indexOf方法；
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (item) {
        var index = -1;
        for (var i = 0; i < this.length; i++) {
            if (this[i] === item) {
                index = i;
                break;
            }
        }
        return index;
    }
}

//List类实现
var List = function (args) {
    var self = this;

    if (List.isArray(args)) {
        self = args;
    }
    self.constructor = List;
    //向集合追加新元素
    self.add = function (item) {
        self.push(item);
        return self;
    };
    //在指定索引处插入新元素
    self.insert = function (index, item) {
        self.splice(index, 0, item);
        return self;
    };
    //删除元素，仅删除第一个索引处的元素
    self.remove = function (item) {
        var index = self.indexOf(item);
        if (index != -1) {
            return self.splice(index, 1)[0];
        } else {
            return undefined;
        }
    };
    //删除元素，如果元素在多个索引处存在，则全部删除
    self.removeAll = function (item) {
        var result = [];
        var removeItem = undefined;
        do {
            removeItem = self.remove(item);
            if (removeItem !== undefined) {
                result.push(removeItem);
            }
        } while (removeItem !== undefined);
        return result;
    };
    //根据index删除元素
    self.removeAt = function (index) {
        if (index != -1) {
            return self.splice(index, 1)[0];
        } else {
            return undefined;
        }
    };
    //判断元素是否包含在集合中
    self.contains = function (item) {
        return self.indexOf(item) != -1;
    };
    //清空集合的所有元素
    self.clear = function () {
        return new List(self.splice(0, self.length));
    };
    self.size = function () {
        return self.length;
    };

    if (List.isArray(args)) {
        return self;
    } else if (arguments.length > 0) {
        for (var i = 0; i < arguments.length; i++) {
            self.add(arguments[i]);
        }
    }
};
List.isArray = Array.isArray || function (target) {
    return Object.prototype.toString.call(target) === "[object Array]";
};

/*下面这段代码是关键，它设定了List类的原型是Array的原型（继承关系），所以List类也同样拥有Array的特征*/
List.prototype = Array.prototype;