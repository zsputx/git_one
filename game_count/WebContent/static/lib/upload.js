/////////////////////与服务段通信/////////////////////
//上传对象并生成js
function write_action() {

    $.ajax({
        url: "../write.do",
        type: 'POST',
        dataType: "json",
        data: {szbd: "var conf=" + JSON.stringify(conf) + "; conf=eval(conf);"},
        success: function (resp) {
            console.log("ok")
        },
        error: function () {
            console.log("error");
        }
    });
}

///上传图片
function doUpload() {
    $.ajax({
        url: '../upload_pic.do',
        type: 'POST',
        cache: false,
        data: new FormData($('#form_tj')[0]),//h5的DataForm对象
        dataType: "json",
        processData: false,
        contentType: false,
        success: function (data) {
            alert(data.msg);
            if (data.msg == "ok") {
                console.log("文件上传成功");

            } else if (data.msg == "have") {
                console.log("文件已存在");
            }
        }
    })
}

/**
 * 向服务端发送对象{infoId:0,gameName:"dd",lookerName:"llk",adName:"namezx",lookTime:"2018"}
 * @param d
 * 类型必须和服务器定义的类型一致
 */
function send_obj(d) {
    $.ajax({
        url: "../json.do",
        type: 'POST',
        dataType: "json",
        data: d,
        success: function (e) {
            console.log(e)
        },
        error: function (err) {
            console.log({err: err})
        }
    });
}

/**
 * 向服务端发送字符串
 * 名称必须和服务端指定的一样
 * @param str
 */
function sendText(str, str1) {

    $.ajax({
        url: "../text.do",
        type: 'POST',
        dataType: "json",
        data: {text: str, haha: str1},
        success: function (resp) {
            console.log("ok", resp.data)
        },
        error: function () {
            console.log("error");
        }
    });
}

//////////////////////////////////设计界面的方法集//////////////////


// /上传图片
var cy_res_all = [];
var cache_url = [];
var have_select = null;
var nameC = 0;
var auto_paly_dsq = null;

//{"name","url"}
//{kind:"pic","name"}
//判断资源库是否含有该资源
function have_res(name) {
    for (var i = 0; i < cy_res_all.length; i++) {
        if (name == cy_res_all[i].name) {
            return true
        }
    }
    return false;
}

function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

function get_url(name) {
    for (var i = 0; i < cache_url.length; i++) {
        if (name == cache_url[i].name) {
            return cache_url[i].url;
        }
    }
    return null;

}

function dis_pic_again(name) {
    var k = get_url(name);
    if (k) {
        document.getElementById("pre_look").src = k;
        $("#pre_listen").hide();
        $("#pre_look").show();
    }

}

function dis_audio_again(name) {
    var k = get_url(name);
    if (k) {
        document.getElementById("pre_listen").src = k;
        $("#pre_listen").show();
        $("#pre_look").hide();
    }
}

function cy_add_res(a) {

    var sp = document.createElement("div");
    sp.class = "res_list";
    if (a.kind == "img") {
        sp.innerHTML = "<div class='res_list_k'>" + "<img style='width=20px;height: 20px;' src='./res/pic_logo2.png'/>" + "<span class='res_list_m'>" + a.name + "</span>" + "</div>";
        conf.res.push({type: "img", id: a.name, url: "./res/" + a.name});
    } else if (a.kind == 'audio') {

        sp.innerHTML = "<div class='res_list_k'>" + "<img style='width=20px;height: 20px;' src='./res/music_logo.png'/>" + "<span class='res_list_p'>" + a.name + "</span>" + "</div>";
        conf.res.push({type: "audio", id: a.name, url: "./res/" + a.name});
    }

    sp.addEventListener("click", function () {
        if (a.kind == "img") {
            dis_pic_again(a.name);
            hava_select = a;
        } else if (a.kind == "audio") {
            dis_audio_again(a.name);
            hava_select = a;
        }
    });
    var res = document.getElementById("res_libs");
    res.appendChild(sp);
    reload_game();

}

//上传资源
$('#file_up').on('change', function () {

    var va = $(this).val();
    var fileex = va.substring(va.lastIndexOf('.') + 1);

    var value = $(this).val().split("\\")[2];

    if (!have_res(value)) {
        if (fileex.localeCompare('mp3') === 0 || fileex.localeCompare('ogg') === 0) {
            //上传的是音乐文件
            var objUrl = getObjectURL(this.files[0]);
            $("#pre_listen").attr("src", objUrl);
            $("#pre_listen")[0].play();
            $("#pre_listen").show();
            $("#pre_look").hide();
            cache_url.push({name: value, url: objUrl});
            cy_res_all.push({name: value, kind: "audio"});
            cy_add_res({name: value, kind: "audio"});

        } else if (fileex.localeCompare('jpg') === 0 || fileex.localeCompare('jpeg') === 0 || fileex.localeCompare('gif') === 0 || fileex.localeCompare('png') === 0) {
            //上传图片文件
            $("#pre_listen").hide();
            $("#pre_look").show();
            var objUrl1 = getObjectURL(this.files[0]);
            $("#pre_look").attr("src", objUrl1);
            cache_url.push({name: value, url: objUrl1});
            cy_res_all.push({name: value, kind: "img"});
            cy_add_res({name: value, kind: "img"});
        }

    } else {
        alert("资源名称重复");
    }
})


function player_action() {
    refresh_player();
    if (document.getElementById("player").style.display == "none") {
        document.getElementById("player").style.display = "block"
        document.getElementById("play_bt").innerHTML = "停止"

    } else {
        document.getElementById("player").style.display = "none";
        document.getElementById("play_bt").innerHTML = "播放"
    }

}


function refresh_player() {
    document.getElementById("player").src = "../static/player/index.html?no=" + Math.random();
}


function change_color() {
    var val = document.getElementById("cy_select_color").value;
    conf.bgColor = changeTo0x(val);
    iframe_cd("set_bgColor", [val], "player");
    set_bgColor(cy_game, val);
}

function cy_set_stage() {
    var pw = parseInt(document.getElementById("cy_input_width").value);
    var ph = parseInt(document.getElementById("cy_input_height").value);

    if (pw) {
        if (ph) {
            if (pw > 10 && pw > 10) {
                conf.w = pw;
                conf.h = ph;
                iframe_cd("set_game_size", [pw, ph], "player");
                set_game_size(cy_game, pw, ph);
            } else {
                alert("输入宽高不合法")
            }
        } else {
            alert("输入宽高不合法")
        }

    } else {
        alert("输入宽高不合法")
    }

    iframe_cd("replay", conf, 'player');
}


//执行iframe中函数
function iframe_cd(v_mymethod, v_params, v_frmName) {
    if (document.getElementById(v_frmName)) {

        var fn = document.getElementById(v_frmName).contentWindow[v_mymethod];

        if (fn) {
            if (v_params == null)
                return fn();
            else {
                return fn.apply(this, v_params);
            }
        } else {

            var f_Iframe = document.getElementById(v_frmName).contentWindow;
            doIfraneFuncRecursion(v_params, v_mymethod, f_Iframe);
        }
        return null;
    }
}

// 递归获取方法
var doIfraneFuncRecursion = function (v_params, v_mymethod, f_Iframe) {
    if (f_Iframe == undefined) {
        return;
    }
    v_fn = f_Iframe.window.frames[0][v_mymethod];
    if (v_fn) {
        if (v_params == null)
            return v_fn();
        else {
            return v_fn.apply(this, v_params);
        }
    } else {
        f_Iframe = f_Iframe.window.frames[0].contentWindow;
        doIfraneFuncRecursion(v_params, v_mymethod, f_Iframe)
    }
}

////////////w网格//

function change_wg(e) {
    if (cy_game.state.states["gameState"].wg.state) {

        cy_game.state.states["gameState"].wg.hide();

        e.value = "显示网格";
    } else {

        cy_game.state.states["gameState"].wg.display();
        e.value = "隐藏网格";
    }
}

function cy_wg_dx(e) {

    var dx = parseInt(e.value);
    if (dx > 1) {
        cy_game.state.states["gameState"].wg.reset_wg(e.value);
    } else {
        alert("网格参数不合法!");
    }
}

function res_to_stage() {

    if (hava_select) {
        if (hava_select.kind == "img") {

            add_mc(new mc_cy("sprite", hava_select.name));
        }
    }

}

//////////////////////////时间轴的方法/////////////////////////////////////////
var frame_yb = 1;
var frame_c = 0;
var select_mc=null;
//Phaser.Game(width, height, renderer, parent, state, transparent, antialias, physicsConfig)
var game_time = new Phaser.Game(1000, 60, Phaser.CANVAS, 'time_line', {
    preload: cy_preload,
    create: cy_create,
    update: cy_update
}, true);

function cy_preload() {
    game_time.load.image("left", './res/arrow_left.png');
    game_time.load.image("right", './res/arrow_right.png');
    game_time.load.image("plu", './res/plu.png');
    game_time.load.image("frame_z", './res/frame_z.png');
    game_time.load.image("jian_hao", './res/jian_hao.png');
}

function cy_create() {
    //game_time.stage.backgroundColor =Phaser.Color.toRGBA(0,200,10,1);
    var frame_group = game_time.add.group();
    var g = game_time.add.graphics();
    g.lineStyle(1, 0xffffff, 1);
    g.drawRect(50, 10, 800, 40);
    g.lineStyle(1, 0xff0000, 1);
    g.drawRect(50, 29, 800, 2);
    g.lineStyle(1, 0xffffff, 1);
    for (var i = 0; i < 40; i++) {
        g.drawRect(50 + i * 20, 10, 20, 40);
    }

    var yb = game_time.add.graphics();
    yb.lineStyle(1, 0xff0000, 1);
    yb.drawRect(60, 8, 1, 44);
    yb.drawRect(50, 15, 20, 30);
    game_time.add.button(880, 30, 'left', function () {
        if (yb.x > 0) {
            yb.x += -20;
            frame_yb += -1;
            dis_scene_yb();
        }
    }).anchor.set(0.5, 0.5)
    game_time.add.button(920, 30, 'right', function () {
        if (yb.x < 780) {
            yb.x += 20;
            frame_yb += 1;
            dis_scene_yb();
        }
    }).anchor.set(0.5, 0.5);
    game_time.add.button(20, 30, 'plu', function () {
        if (frame_c < 40) {
            frame_c += 1;
            var f = game_time.make.sprite(40 + 20 * frame_c, 30, 'frame_z');
            f.anchor.set(0.5, 0.5);
            f.width = 3;
            f.name = "framei" + frame_c;
            frame_group.addChild(f);
            add_scene();
            console.log("d1");
        }
    }).anchor.set(0.5, 0.5);

    game_time.add.button(980, 30, 'jian_hao', function () {
        if (frame_c > 0) {
            frame_group.getByName("framei" + frame_c).destroy();
            jian_scene(frame_c);
            frame_c += -1;

        }

    }).anchor.set(0.5, 0.5);

}

function cy_update() {


}

function play_scene_auto() {
    if (conf.auto_play_kg) {
        console.log("ks");
        var sz = [];
        var i = 0;
        var time_c = conf.auto_play_time;//播放次数
        for (var j = 0; j < conf.scene.length; j++) {
            sz.push(conf.scene[j].time);
        }

        auto_paly_dsq = setInterval(function () {

            sz[i] += -1;
            console.log("play第", time_c, "次数", sz[i]);
            paly_zd_scene(i + 1);
            if (sz[i] <= 0) {
                pause_zd_scene(i + 1);
                if (i < frame_c - 1) {
                    i++;
                    console.log("切换", sz[i]);
                } else {
                    //一遍播放完毕

                    if (conf.auto_play_time > 0) {
                        //播放固定次数

                        time_c += -1;
                        if (time_c <= 0) {
                            clearInterval(auto_paly_dsq);
                        } else {
                            i = 0;
                            sz = [];
                            for (var j1 = 0; j1 < conf.scene.length; j1++) {
                                sz.push(conf.scene[j1].time);
                            }
                            console.log(sz);
                        }
                    } else {
                        //循环播放
                        i = 0;
                        sz = [];
                        for (var j11 = 0; j11 < conf.scene.length; j11++) {
                            sz.push(conf.scene[j11].time);
                        }

                    }

                }
            }


        }, 1000);
    } else {
        if (auto_paly_dsq) {
            clearInterval(auto_paly_dsq);
        }

    }
}

function cy_auto_paly(e) {

    //有场景
    if (frame_c > 0) {

        if (conf.auto_play_kg) {
            //停止
            conf.auto_play_kg = false;
            play_scene_auto();
            e.value = "自动播放";
        } else {
            //播放
            conf.auto_play_kg = true;
            play_scene_auto();
            e.value = "停止播放";

        }


    } else {
        alert("请先创建场景");
    }

}

//////////////////////////////指导舞台场景的方法/////////////////////////////////////
//增加场景
function add_scene() {

    var g = cy_game.add.group();
    g.name = "cy_scene" + frame_c;
    g.visible = false;
    conf.scene.push(new scene_cy(frame_c));
    console.log("d2");
}

function jian_scene(a) {
    for (var i = 0; i < conf.scene.length; i++) {
        if (conf.scene[i].name == "cy_scene" + a) {
            conf.scene.splice(1, i);
        }
    }
    if (cy_game.world.getByName("cy_scene" + a)) {
        cy_game.world.getByName("cy_scene" + a).destroy(true);
    }
}

//随着游标的移动，动态显示
function dis_scene_yb() {
    // frame_group.getByName("framei" + frame_c)
    for (var i = 0; i <= frame_c; i++) {
        var g = cy_game.world.getByName("cy_scene" + i);
        if (g) {
            g.visible = false;
            g.setAll("visible", false);
        }
        //g=null;
    }


    if (frame_yb <= frame_c) {
        var g2 = cy_game.world.getByName("cy_scene" + frame_yb);
        if (g2) {
            g2.visible = true;
            g2.setAll("visible", true);
        }
        // g2=null;
    }
}

function paly_zd_scene(i) {
    if (cy_game.world.getByName("cy_scene" + i)) {
        cy_game.world.getByName("cy_scene" + i).visible = true;
        cy_game.world.getByName("cy_scene" + i).setAll("visible", true);
    }
}

function pause_zd_scene(i) {
    if (cy_game.world.getByName("cy_scene" + i)) {
        cy_game.world.getByName("cy_scene" + i).visible = false;
        cy_game.world.getByName("cy_scene" + i).setAll("visible", false);
    }
}

function get_current_scene() {
    if (frame_yb <= frame_c) {
        return cy_game.world.getByName("cy_scene" + frame_yb);
    } else {
        alert("没有该场景");
        return null;
    }
}

function get_conf_scene(name) {


    for (var i = 0; i < conf.scene.length; i++) {
        if (conf.scene[i].name = name) {
            return conf.scene[i];

        }
    }
    return null;
}

function add_mc(a) {
    var sc = get_current_scene();
    if (sc) {
        if (a.kind == 'sprite') {
            nameC++;
            var s = cy_game.make.sprite(a.x, a.y, a.src);
            s.name = sc.name + "mc" + nameC;
            a.name = sc.name + "mc" + nameC;
            s.anchor.set(0.5, 0.5);
            s.scale.set(a.scaleX, a.scaleY);
            s.rotation = a.rotation;
            s.visible = true;
            s.alpha = a.alpha;
            s.tint = a.tint;
            s.width = a.w;
            s.height = a.h;
            s.update = a.update;
            s.inputEnabled = true;

            // 允许拖拽，第一个参数true代表拖拽的时候鼠标位于精灵中心
            s.input.enableDrag(true);
            s.events.onDragStop.add(function () {
                set_mc_pro(s);
                huanyuan_val(s);

            });
            s.events.onInputDown.add(function(){
                select_mc=s;
                huanyuan_val(select_mc);
            });

            sc.addChild(s);
            var sn = get_conf_scene("cy_scene" + frame_yb)
            if (sn) {
                sn.children.push(a);
            }

        }
    }

}

function cy_set_w(e){
    if(select_mc){
        select_mc.width=parseInt(e.value);
        set_mc_pro(select_mc)
    }

}
function cy_set_h(e){
    if(select_mc){
        select_mc.height=parseInt(e.value);
        set_mc_pro(select_mc)
    }

}
function cy_set_x(e){
    if(select_mc){
        select_mc.x=parseInt(e.value);
        set_mc_pro(select_mc)
    }

}

function cy_set_y(e){
    if(select_mc){
        select_mc.y=parseInt(e.value);
        set_mc_pro(select_mc);
    }
}

function cy_set_rotation(e){
    if(select_mc){
        select_mc.rotation=parseInt(e.value);
        set_mc_pro(select_mc);
    }
}
function cy_set_alpha(e){
    if(select_mc){
        select_mc.alpha=parseFloat(e.value);
        set_mc_pro(select_mc);
    }
}
function cy_set_tint(e){
    if(select_mc){
        select_mc.tint=changeTo0x(e.value);
       console.log(changeTo0x(e.value));
        set_mc_pro(select_mc);
    }
}
function cy_set_sx(e){
    if(select_mc){
        select_mc.scale.x=parseFloat(e.value);
        set_mc_pro(select_mc);
    }
}


function cy_set_sy(e){
    if(select_mc){
        select_mc.scale.y=parseFloat(e.value);
        set_mc_pro(select_mc);
    }
}

function huanyuan_val(){
if(select_mc){
    document.getElementById("cy_x_val").value=select_mc.x;
    document.getElementById("cy_y_val").value=select_mc.y;
    document.getElementById("cy_rotation_val").value=select_mc.rotation;
    document.getElementById("cy_alpha_val").value=select_mc.alpha;
    document.getElementById("cy_sx_val").value=select_mc.scale.x;
    document.getElementById("cy_sy_val").value=select_mc.scale.y;
    document.getElementById("cy_w_val").value=select_mc.width;
    document.getElementById("cy_h_val").value=select_mc.height;
    document.getElementById("cy_tint_val").value="#"+(select_mc.tint.toString(16)).substr(0,8);
}
}

function changeTo0x(val){
   return eval(("0x"+val.substr(1,6)));
}

function set_mc_pro(a) {
    var sc = get_conf_scene("cy_scene" + frame_yb);
    if (sc) {
        for (var i = 0; i < sc.children.length; i++) {

            if (a.name == sc.children[i].name) {
                sc.children[i].x = a.x;
                sc.children[i].y = a.y;
                sc.children[i].scaleX = a.scaleX;
                sc.children[i].scaleY = a.scaleY;
                sc.children[i].rotation = a.rotation;
                sc.children[i].visible=a.visible;
                sc.children[i].alpha = a.alpha;
                sc.children[i].tint = a.tint;
                sc.children[i].w = a.width;
                sc.children[i].h = a.height;
                sc.children[i].update = a.update;
            }
        }
    }
}

function set_game_size(game, w, h) {
    game.scale.setGameSize(w, h);
    game.width = w;
    game.height = h;
    console.log("game------------------------------------");
}

function set_bgColor(game, val) {
    game.stage.backgroundColor = val;
}

function reload_game() {
    cy_game.state.start("loadState");

}