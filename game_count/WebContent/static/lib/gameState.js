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
var GameState = (function (_super) {
    __extends(GameState, _super);
    function GameState(game) {
        var _this = _super.call(this) || this;
        _this.game = game;
        return _this;
    }
    GameState.prototype.preload = function () {
        this.w = this.game.world.width;
        this.h = this.game.world.height;
    };
    GameState.prototype.create = function () {
        var me = this;
        this.game.stage.backgroundColor = conf.bgColor;
        this.wg = new Wg(this);
        this.wg.create_wg();
        Restore.create(this.game);
        this.game.input.onDown.add(function () {
        });
    };
    return GameState;
}(Phaser.State));
