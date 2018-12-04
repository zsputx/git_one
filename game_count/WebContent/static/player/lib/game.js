var game = new Phaser.Game(conf.w, conf.h, conf.render, 'super',true);


game.States = {};


var PreloadScene={
    preload:function(){
        Util.load_res(game,conf.res);
        console.log("加载资源完成");
    },
    create:function(){
        game.state.start('main');
        console.log("跳入下一场景");
    }
}
var MainScene={
    create:function(){

        console.log("进入主场景");
      ///  Util.setBackColor(game,0xfff00f);
        for (var i = 0; i < 15; i++) {
            // 随机建立一个精灵
            var sprite = game.add.sprite(game.world.randomX, game.world.randomY, 'zw');
            // 获取-2到6的一个随机数
            var rand = game.rnd.realInRange(-2, 6);
            // 设置缩放
            sprite.scale.setTo(0.1, 0.5);
            // 也可以这样设置缩放
            // sprite.scale.x = rand;
            // sprite.scale.y = rand;
        }
        createb(conf);


    },
    update:function(){


    }

}
// 预加载场景，用于加载资源
game.States.preload = function() {
    this.preload = PreloadScene.preload;
    this.create = PreloadScene.create;
}

game.States.main = function() {
    this.create = MainScene.create.bind(MainScene);
    this.update = MainScene.update.bind(MainScene);
};


game.state.add('preload', game.States.preload);
game.state.add('main', game.States.main);
game.state.start('preload');


/**
 * 外部控制函数
 *
 */

function set_game_size(w,h){
    game.scale.setGameSize(w,h)
    console.log("game------------------------------------");
}
function set_bgColor(val) {
    game.stage.backgroundColor=val;
}

function replay(a){
    conf=a;
    game.state.start('preload');
}

function createb(conf){
    game.stage.backgroundColor=conf.bgColor;
    game.scale.setGameSize(conf.w,conf.h);
    for(var i=0;i<conf.scene.length;i++){
        var k=game.add.group();
        k.name=conf.scene[i].name;
        k.visible=true;
        for(var j=0;j<conf.scene[i].children.length;j++) {
            var a = conf.scene[i].children[j];
            if (a.kind == "sprite") {
                var m = game.make.sprite(a.x, a.y, a.src);
                m.name = a.name;
                m.anchor.set(0.5, 0.5);
                m.scale.set(a.scaleX, a.scaleY);
                m.rotation = a.rotation;
                m.visible = a.visible
                m.alpha = a.alpha;
                m.tint = a.tint;
                m.width = a.w;
                m.height = a.h;
                // m.update = a.update;
                k.addChild(m);
            }
        }
    }

}
 


