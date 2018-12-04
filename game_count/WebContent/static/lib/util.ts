/**
 * 网格类
 */
class Wg{
    sys;
    cy_wg;//网格
    cy_wg_g;//绘图对象;
    cy_w_count;//横向网格数
    cy_h_count;//纵向网格数
    cy_jg_count;//网格间隔
    w;
    h;
    state=false;
    constructor(sys){
       this.sys=sys;
       this.state=true;
        this.cy_jg_count=20;
        this.w=sys.game.width;
        this.h=sys.game.height;
        this.cy_w_count=Math.floor(this.w/20);
        this.cy_h_count=Math.floor(this.h/20);
        this.cy_wg=this.sys.game.add.group();
        this.cy_wg_g=this.sys.game.make.graphics();
        this.cy_wg.addChild(this.cy_wg_g);
    }
    //创建网格
    public  create_wg(color?){
        this.cy_wg_g.clear();
        if(color) {
            this.cy_wg_g.lineStyle(1, color, 0.8);
        }else{
            this.cy_wg_g.lineStyle(1, 0xffffff, 0.8);

        }
         for(var i=0;i<=this.cy_w_count;i++){
             this.cy_wg_g.moveTo(i* this.cy_jg_count,0);
             this.cy_wg_g.lineTo(i* this.cy_jg_count,this.h);
         }
        for(var j=0;j<=this.cy_h_count;j++){
            this.cy_wg_g.moveTo(0,j* this.cy_jg_count);
            this.cy_wg_g.lineTo(this.w,j* this.cy_jg_count);
        }



    }
    //设置网格
    public reset_wg(k,color?){
        if(k>1) {
            this.w=this.sys.game.width;
            this.h=this.sys.game.height;
            this.cy_jg_count=k;
            this.cy_w_count = Math.floor(this.w /k);
            this.cy_h_count = Math.floor(this.h /k);
            if(color) {
                this.create_wg(color);
            }else{
                this.create_wg();
            }
        }
    }

    hide(){
        this.cy_wg.visible=false;
        this.state=false;
    }
    display(){
        this.cy_wg.visible=true;
        this.state=true;

    }

}

/**
 * 工具类
 */
class Util{
    /**
     * s随机颜色
     * @returns {any}
     */
    public static randomColor(){
        var rand = Math.floor(Math.random( ) * 0xFFFFFF)
            var t=rand.toString(16);
        if(t.length == 6){
            return rand;
        }else{
            return Util.randomColor();
        }
    }

    /**
     * 设置游戏界面大小
     * @param me
     * @param w
     * @param h
     */
    public static setSysSize(me,w,h){
        me.game.width=w;
        me.game.height=h;
        me.scale.setGameSize( me.game.width,me.game.height);
    }
    public static setBackColor(game,color){
        game.stage.backgroundColor = color;
    }

    /**
     * 加载资源
     * @param game
     * @param res
     */
    public static load_res(game,res){

        for(var i=0;i<res.length;i++){
            if(res[i].type=="img") {
                game.load.image(res[i].id, res[i].url);

            }else if(res[i].type=="audio"){
                game.load.audio(res[i].id, res[i].url);

            }
        }
    }


    public static coppyObj(obj){

            var newObj = {};
            if (obj instanceof Array) {
                newObj = [];
            }
            for (var key in obj) {
                var val = obj[key];
                //newObj[key] = typeof val === 'object' ? arguments.callee(val) : val; //arguments.callee 在哪一个函数中运行，它就代表哪个函数, 一般用在匿名函数中。
                newObj[key] = typeof val === 'object' ? Util.coppyObj(val): val;
            }
            return newObj;
        }


}

class txList extends Array{



    constructor(){
        super();
    }

    public add(){

    }
    public remove(){


    }
}

