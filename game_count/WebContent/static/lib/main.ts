var cy_game=null;
class G1_Game {
    game;
    loadState;
    gameState;
    constructor() {
    this.game = new Phaser.Game({"width":conf.w, "height":conf.h, "renderer":Phaser.CANVAS,"parent":"super",transparent:true});//"resolution":window.devicePixelRatio
    this.loadState = new LoadState(this.game);
    this.gameState = new GameState(this.game);
    this.game.state.add("loadState", this.loadState);
    this.game.state.add("gameState", this.gameState);
    this.game.state.start("loadState");
    cy_game=this.game;
    }
}

window.onload = () => {
    var game = new G1_Game();
};









