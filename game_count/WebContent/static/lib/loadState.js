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
//import Phaser from '../../libs/phaser-wx.js'
var LoadState = (function (_super) {
    __extends(LoadState, _super);
    function LoadState(game) {
        var _this = _super.call(this) || this;
        _this.game = game;
        return _this;
    }
    LoadState.prototype.preload = function () {
        this.game.stage.backgroundColor = 0xf84803;
        this.game.load.image('pro_bar', 'res/pro_bar.png');
    };
    LoadState.prototype.create = function () {
        this.load_anm();
        this.game.load.onFileComplete.add(this.filePross, this);
        this.game.load.onLoadComplete.add(this.loadComplete, this);
        this.load_res(this.game);
    };
    LoadState.prototype.load_res = function (game) {
        game.load.image('img_sun', 'res/img_sun.png');
        game.load.image('star', 'res/star.png');
        Util.load_res(this.game, conf.res);
        game.load.start();
    };
    LoadState.prototype.load_anm = function () {
        this.anm = this.game.add.sprite(this.game.world.centerX - 100, this.game.world.centerY, 'pro_bar');
        this.anm.scale.set(1, 0.2);
        this.anm.width = 0;
        this.text_progress = this.game.add.text(this.game.world.centerX, this.game.world.centerY - 30, "0%", { font: "20px Arial", fill: "orange" });
        this.text_progress.anchor.set(0.5, 0.5);
    };
    //加载资源
    // loadStart(){}
    //资源进度
    LoadState.prototype.filePross = function (progress, cacheKey, success, totalLoaded, totalFiles) {
        this.text_progress.text = progress + "%";
        this.anm.width = window.devicePixelRatio * 200 * progress / 100;
    };
    //资源完成
    LoadState.prototype.loadComplete = function () {
        this.game.state.start('gameState');
    };
    return LoadState;
}(Phaser.State));
//module.exports=LoadState;
