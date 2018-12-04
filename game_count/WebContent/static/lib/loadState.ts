//import Phaser from '../../libs/phaser-wx.js'
class LoadState extends Phaser.State {
    game;
    anm;
    text_progress;
    constructor(game) {
        super();
        this.game = game;
    }

    preload() {
        this.game.stage.backgroundColor=0xf84803;
        this.game.load.image('pro_bar','res/pro_bar.png');

    }

    create() {
        this.load_anm();
        this.game.load.onFileComplete.add(this.filePross, this);
        this.game.load.onLoadComplete.add(this.loadComplete, this);

        this.load_res(this.game);

    }

    load_res(game){


        game.load.image('img_sun', 'res/img_sun.png');
        game.load.image('star', 'res/star.png');
        Util.load_res(this.game,conf.res);
        game.load.start();

    }

    load_anm(){
        this.anm=this.game.add.sprite(this.game.world.centerX-100,this.game.world.centerY,'pro_bar');
        this.anm.scale.set(1,0.2);
        this.anm.width=0;
        this.text_progress=this.game.add.text(this.game.world.centerX,this.game.world.centerY-30,"0%",{ font: "20px Arial", fill: "orange"});
        this.text_progress.anchor.set(0.5,0.5);

    }
    //加载资源
    // loadStart(){}
    //资源进度
    filePross(progress, cacheKey, success, totalLoaded, totalFiles){
        this.text_progress.text=progress+"%";
        this.anm.width=window.devicePixelRatio*200*progress/100;

    }
    //资源完成
    loadComplete(){
        this.game.state.start('gameState');

    }





}


//module.exports=LoadState;


