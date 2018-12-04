/**
 * 配置文件
 * @constructor
 */
var Config = (function () {
    function Config() {
        this.kind = "game";
        this.w = 320;
        this.h = 568;
        this.render = "CANVAS";
        this.bgColor = Phaser.Color.toRGBA(0.1, 0.1, 0.1, 0);
        this.res = [{ type: 'img', id: 'zw', url: './res/zw.png' }, { type: 'img', id: 'yp', url: './res/yp.png' }];
        this.scene = [];
        this.action = [];
        this.auto_play_kg = false;
        this.auto_play_time = -1; // -1代表循环
    }
    return Config;
}());
/**
 * 场景
 * @param a
 */
var scene_cy = (function () {
    function scene_cy(a) {
        this.name = "cy_scene" + a;
        this.children = [];
        this.visible = false;
        this.selfPlay = false;
        this.time = 4;
        this.frame_num = a;
        this.preFrame_num = a - 1;
        this.nextFrame_num = a + 1;
    }
    return scene_cy;
}());
/**
 * 元件
 * @param kind
 * @param src
 */
var mc_cy = (function () {
    function mc_cy(kind, src) {
        this.update = function () {
        };
        this.mouseAction = function () {
        };
        this.name = "";
        this.kind = kind; //"sprite"//"text"//""particle//button
        this.src = src;
        this.visible = true;
        this.alpha = 1;
        this.tint = 0xffffff;
        this.w = 100;
        this.h = 100;
        this.x = 100;
        this.y = 100;
        this.anchorX = 0.5;
        this.anchorY = 0.5;
        this.scaleX = 1;
        this.scaleY = 1;
        this.rotation = 0;
        this.update = function () {
        };
        this.mouseAction = function () {
        };
    }
    return mc_cy;
}());
var Restore = (function () {
    function Restore() {
    }
    Restore.create = function (game) {
        for (var i = 0; i < conf.scene.length; i++) {
            var k = game.add.group();
            k.name = conf.scene[i].name;
            k.visible = conf.scene[i].visible;
            for (var j = 0; j < conf.scene[i].children.length; j++) {
                var a = conf.scene[i].children[j];
                if (a.kind == "sprite") {
                    var m = game.make.sprite(a.x, a.y, a.src);
                    m.name = a.name;
                    m.anchor.set(0.5, 0.5);
                    m.scale.set(a.scaleX, a.scaleY);
                    m.rotation = a.rotation;
                    m.visible = a.visible;
                    m.alpha = a.alpha;
                    m.tint = a.tint;
                    m.width = a.w;
                    m.height = a.h;
                    ////   m.update = a.update;
                    k.addChild(m);
                }
            }
        }
    };
    return Restore;
}());
/**
 * kind{
 * 创建
 * cmd:create;
 * md:'stage'/group;
 * kind: sprite;/graphics/button/ audio/praticle
 * pro{}
 *
 * 修改
 *
 * cmd update;
 * md mc
 * pro{}
 *
 *
 * cmd distroy
 *
 * }
 *
 */
var action_cy = (function () {
    function action_cy() {
    }
    return action_cy;
}());
//
var conf = new Config();
/*
编辑器

核心原理

1操纵编辑器 编辑 动画游戏效果
    2动态生成元码级对象
    （源码级对象有 设备配置对象
    （屏幕宽高适配，资源配置对象（图片 声音等资源））
    关联树对象
    场景对象
    元件对象
    事件对象
    物理对象 ）
    3将动态元码写入js文件
    4 解析元码对象生成游戏动画（可使用不同的引擎解析）

    工作内容；
    1构建元码对象模板
    2 构建编辑器
    3 构建解析元码对象的程序

  生成元码对象种类
   1动画
   2 交互 ppt
   3 游戏




 */ 
