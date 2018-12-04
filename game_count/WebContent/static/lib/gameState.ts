//import Phaser from '../../libs/phaser-wx.js'
class GameState extends Phaser.State {

    constructor(game) {
        super();
        this.game = game;
    }

    //所需全局变量

    w;
    h;
    wg;
    preload() {
        this.w = this.game.world.width;
        this.h = this.game.world.height;

    }








    create() {
        var me=this;

        this.game.stage.backgroundColor = conf.bgColor;
       this.wg=new Wg(this);
        this.wg.create_wg();

        Restore.create(this.game);


        this.game.input.onDown.add(function(){
        })

    }




}




