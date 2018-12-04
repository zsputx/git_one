var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/**
 * 网格类
 */
var Wg = (function () {
    function Wg(sys) {
        this.state = false;
        this.sys = sys;
        this.state = true;
        this.cy_jg_count = 20;
        this.w = sys.game.width;
        this.h = sys.game.height;
        this.cy_w_count = Math.floor(this.w / 20);
        this.cy_h_count = Math.floor(this.h / 20);
        this.cy_wg = this.sys.game.add.group();
        this.cy_wg_g = this.sys.game.make.graphics();
        this.cy_wg.addChild(this.cy_wg_g);
    }
    //创建网格
    Wg.prototype.create_wg = function (color) {
        this.cy_wg_g.clear();
        if (color) {
            this.cy_wg_g.lineStyle(1, color, 0.8);
        }
        else {
            this.cy_wg_g.lineStyle(1, 0xffffff, 0.8);
        }
        for (var i = 0; i <= this.cy_w_count; i++) {
            this.cy_wg_g.moveTo(i * this.cy_jg_count, 0);
            this.cy_wg_g.lineTo(i * this.cy_jg_count, this.h);
        }
        for (var j = 0; j <= this.cy_h_count; j++) {
            this.cy_wg_g.moveTo(0, j * this.cy_jg_count);
            this.cy_wg_g.lineTo(this.w, j * this.cy_jg_count);
        }
    };
    //设置网格
    Wg.prototype.reset_wg = function (k, color) {
        if (k > 1) {
            this.w = this.sys.game.width;
            this.h = this.sys.game.height;
            this.cy_jg_count = k;
            this.cy_w_count = Math.floor(this.w / k);
            this.cy_h_count = Math.floor(this.h / k);
            if (color) {
                this.create_wg(color);
            }
            else {
                this.create_wg();
            }
        }
    };
    Wg.prototype.hide = function () {
        this.cy_wg.visible = false;
        this.state = false;
    };
    Wg.prototype.display = function () {
        this.cy_wg.visible = true;
        this.state = true;
    };
    return Wg;
}());
/**
 * 工具类
 */
var Util = (function () {
    function Util() {
    }
    /**
     * s随机颜色
     * @returns {any}
     */
    Util.randomColor = function () {
        var rand = Math.floor(Math.random() * 0xFFFFFF);
        var t = rand.toString(16);
        if (t.length == 6) {
            return rand;
        }
        else {
            return Util.randomColor();
        }
    };
    /**
     * 设置游戏界面大小
     * @param me
     * @param w
     * @param h
     */
    Util.setSysSize = function (me, w, h) {
        me.game.width = w;
        me.game.height = h;
        me.scale.setGameSize(me.game.width, me.game.height);
    };
    Util.setBackColor = function (game, color) {
        game.stage.backgroundColor = color;
    };
    /**
     * 加载资源
     * @param game
     * @param res
     */
    Util.load_res = function (game, res) {
        for (var i = 0; i < res.length; i++) {
            if (res[i].type == "img") {
                game.load.image(res[i].id, res[i].url);
            }
            else if (res[i].type == "audio") {
                game.load.audio(res[i].id, res[i].url);
            }
        }
    };
    Util.coppyObj = function (obj) {
        var newObj = {};
        if (obj instanceof Array) {
            newObj = [];
        }
        for (var key in obj) {
            var val = obj[key];
            //newObj[key] = typeof val === 'object' ? arguments.callee(val) : val; //arguments.callee 在哪一个函数中运行，它就代表哪个函数, 一般用在匿名函数中。
            newObj[key] = typeof val === 'object' ? Util.coppyObj(val) : val;
        }
        return newObj;
    };
    return Util;
}());
var txList = (function (_super) {
    __extends(txList, _super);
    function txList() {
        return _super.call(this) || this;
    }
    txList.prototype.add = function () {
    };
    txList.prototype.remove = function () {
    };
    return txList;
}(Array));
