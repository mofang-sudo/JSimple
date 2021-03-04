

var E = window.wangEditor
var editor = new E('#editor')
editor.customConfig.colors = [
    '#000000',
    '#eeece0',
    '#1c487f',
    '#4d80bf',
    '#ff0000',
    '#00FF00',
    '#7b5ba1',
    '#46acc8',
    '#f9963b',
    '#ffffff'
]
editor.customConfig.pasteFilterStyle = false
editor.customConfig.uploadImgServer = '/file/upload';  // 上传图片到服务器
editor.customConfig.uploadFileName = 'myFile';//设置文件上传的参数名称
editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;//最大尺寸1M
editor.customConfig.uploadImgMaxLength = 5;//最多同时上传5张
editor.customConfig.menus = [
    'head',  // 标题
    'bold',  // 粗体
    'fontSize',  // 字号
    'foreColor',  // 文字颜色
    'emoticon',  // 表情
    'image',  // 插入图片
    'link',  // 插入链接
    'code',  // 插入代码
    'undo',  // 撤销
    'redo',  // 重复
    'fontName',  // 字体
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'backColor',  // 背景颜色
    'list',  // 列表
    'justify',  // 对齐方式
    'quote',  // 引用
    'table',  // 表格
    'video',  // 插入视频
    'nvideo'
]
editor.customConfig.customAlert = function (info) {
    // info 是需要提示的内容
    swal("上传失败!", info, "error");
}
editor.customConfig.emotions =[
    {
        // tab 的标题
        title: '微博',
        // type -> 'emoji' / 'image'
        type: 'image',
        // content -> 数组
        content: [{alt: '[微笑]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/e3/2018new_weixioa02_org.png'
        },{alt: '[嘻嘻]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/33/2018new_xixi_thumb.png'
        },{alt: '[哈哈]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/8f/2018new_haha_thumb.png'
        },{alt: '[可爱]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/09/2018new_keai_org.png'
        },{alt: '[可怜]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/96/2018new_kelian_org.png'
        },{alt: '[挖鼻]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/9a/2018new_wabi_thumb.png'
        },{alt: '[吃惊]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/49/2018new_chijing_org.png'
        },{alt: '[害羞]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c1/2018new_haixiu_org.png'
        },{alt: '[挤眼]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/43/2018new_jiyan_org.png'
        },{alt: '[闭嘴]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/2018new_bizui_org.png'
        },{alt: '[鄙视]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/da/2018new_bishi_org.png'
        },{alt: '[爱你]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/f6/2018new_aini_org.png'
        },{alt: '[泪]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/2018new_leimu_org.png'
        },{alt: '[偷笑]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/71/2018new_touxiao_org.png'
        },{alt: '[亲亲]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/2c/2018new_qinqin_thumb.png'
        },{alt: '[生病]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/3b/2018new_shengbing_thumb.png'
        },{alt: '[太开心]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/1e/2018new_taikaixin_org.png'
        },{alt: '[白眼]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/ef/2018new_landelini_org.png'
        },{alt: '[右哼哼]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c1/2018new_youhengheng_thumb.png'
        },{alt: '[左哼哼]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/43/2018new_zuohengheng_thumb.png'
        },{alt: '[嘘]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/b0/2018new_xu_org.png'
        },{alt: '[衰]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a2/2018new_shuai_thumb.png'
        },{alt: '[委屈]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a5/2018new_weiqu_thumb.png'
        },{alt: '[吐]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/08/2018new_tu_org.png'
        },{alt: '[哈欠]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/55/2018new_dahaqian_org.png'
        },{alt: '[憧憬]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c9/2018new_chongjing_org.png'
        },{alt: '[怒]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/f6/2018new_nu_thumb.png'
        },{alt: '[疑问]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/b8/2018new_ningwen_org.png'
        },{alt: '[馋嘴]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/fa/2018new_chanzui_org.png'
        },{alt: '[拜拜]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/fd/2018new_baibai_thumb.png'
        },{alt: '[思考]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/30/2018new_sikao_org.png'
        },{alt: '[汗]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/28/2018new_han_org.png'
        },{alt: '[困]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/3c/2018new_kun_thumb.png'
        },{alt: '[睡]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/e2/2018new_shuijiao_thumb.png'
        },{alt: '[钱]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a2/2018new_qian_thumb.png'
        },{alt: '[失望]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/2018new_shiwang_org.png'
        },{alt: '[酷]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c4/2018new_ku_org.png'
        },{alt: '[色]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/9d/2018new_huaxin_org.png'
        },{alt: '[哼]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/7c/2018new_heng_org.png'
        },{alt: '[鼓掌]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/2018new_guzhang_thumb.png'
        },{alt: '[晕]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/07/2018new_yun_thumb.png'
        },{alt: '[悲伤]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/ee/2018new_beishang_org.png'
        },{alt: '[抓狂]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/17/2018new_zhuakuang_org.png'
        },{alt: '[黑线]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a3/2018new_heixian_org.png'
        },{alt: '[阴险]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/9e/2018new_yinxian_org.png'
        },{alt: '[怒骂]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/87/2018new_zhouma_org.png'
        },{alt: '[互粉]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/86/2018new_hufen02_org.png'
        },{alt: '[心]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/8a/2018new_xin_org.png'
        },{alt: '[伤心]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6c/2018new_xinsui_org.png'
        },{alt: '[猪头]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/1c/2018new_zhutou_thumb.png'
        },{alt: '[doge]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a1/2018new_doge02_org.png'
        },{alt: '[二哈]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/22/2018new_erha_org.png'
        },{alt: '[ok]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/45/2018new_ok_org.png'
        },{alt: '[耶]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/29/2018new_ye_thumb.png'
        },{alt: '[good]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/8a/2018new_good_org.png'
        },{alt: '[NO]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/1e/2018new_no_org.png'
        },{alt: '[赞]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/e6/2018new_zan_org.png'
        },{alt: '[来]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/42/2018new_guolai_thumb.png'
        },{alt: '[弱]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/3d/2018new_ruo_org.png'
        },{alt: '[给力]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/36/2018new_geili_org.png'
        },{alt: '[平安灯]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/feiyan_dianliangpingan_thumb.png'
        },{alt: '[炸鸡腿]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/8c/yunying_zhaji_thumb.png'
        },{alt: '[中国赞]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/2018new_zhongguozan_org.png'
        },{alt: '[锦鲤]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/94/hbf2019_jinli_thumb.png'
        },{alt: '[拥抱]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/42/2018new_baobao_thumb.png'
        },{alt: '[威武]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/2018new_weiwu_org.png'
        },{alt: '[摊手]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/2018new_tanshou_org.png'
        },{alt: '[礼物]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/0e/2018new_liwu_org.png'
        },{alt: '[跪了]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/75/2018new_gui_org.png'
        },{alt: '[话筒]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/48/2018new_huatong_org.png'
        },{alt: '[蜡烛]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/16/2018new_lazhu_org.png'
        },{alt: '[蛋糕]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/f9/2018new_dangao_org.png'
        },{alt: '[蛋糕]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/f9/2018new_dangao_org.png'
        },{alt: '[酸]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/b3/hot_wosuanle_thumb.png'
        },{alt: '[武汉加油]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/02/hot_wuhanjiayou_thumb.png'
        },{alt: '[哪吒委屈]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d4/nezha_weiqu02_thumb.png'
        },{alt: '[哪吒得意]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/1d/nezha_deyi02_thumb.png'
        },{alt: '[哪吒开心]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/35/nezha_kaixin02_thumb.png'
        },{alt: '[星星]',src: 'https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/76/hot_star171109_thumb.png'

        }]
    },
    {
        // tab 的标题
        title: '贴吧',
        // type -> 'emoji' / 'image'
        type: 'image',
        // content -> 数组
        content: [
            {
                'src': '/images/smile/tieba/1.png'
            },
            {
                'src': '/images/smile/tieba/2.png'
            },
            {
                'src': '/images/smile/tieba/3.png'
            },
            {
                'src': '/images/smile/tieba/4.png'
            },
            {
                'src': '/images/smile/tieba/5.png'
            },
            {
                'src': '/images/smile/tieba/6.png'
            },
            {
                'src': '/images/smile/tieba/7.png'
            },
            {
                'src': '/images/smile/tieba/8.png'
            },
            {
                'src': '/images/smile/tieba/9.png'
            },
            {
                'src': '/images/smile/tieba/10.png'
            },
            {
                'src': '/images/smile/tieba/11.png'
            },
            {
                'src': '/images/smile/tieba/12.png'
            },
            {
                'src': '/images/smile/tieba/13.png'
            },
            {
                'src': '/images/smile/tieba/14.png'
            },
            {
                'src': '/images/smile/tieba/15.png'
            },
            {
                'src': '/images/smile/tieba/16.png'
            },
            {
                'src': '/images/smile/tieba/17.png'
            },
            {
                'src': '/images/smile/tieba/18.png'
            },
            {
                'src': '/images/smile/tieba/19.png'
            },
            {
                'src': '/images/smile/tieba/20.png'
            },
            {
                'src': '/images/smile/tieba/21.png'
            },
            {
                'src': '/images/smile/tieba/22.png'
            },
            {
                'src': '/images/smile/tieba/23.png'
            },
            {
                'src': '/images/smile/tieba/24.png'
            },
            {
                'src': '/images/smile/tieba/25.png'
            },
            {
                'src': '/images/smile/tieba/26.png'
            },
            {
                'src': '/images/smile/tieba/27.png'
            },
            {
                'src': '/images/smile/tieba/28.png'
            },
            {
                'src': '/images/smile/tieba/29.png'
            },
            {
                'src': '/images/smile/tieba/30.png'
            },
            {
                'src': '/images/smile/tieba/31.png'
            },
            {
                'src': '/images/smile/tieba/32.png'
            },
            {
                'src': '/images/smile/tieba/33.png'
            },
            {
                'src': '/images/smile/tieba/34.png'
            },
            {
                'src': '/images/smile/tieba/35.png'
            },
            {
                'src': '/images/smile/tieba/36.png'
            },
            {
                'src': '/images/smile/tieba/37.png'
            },
            {
                'src': '/images/smile/tieba/38.jpg'
            },
            {
                'src': '/images/smile/tieba/39.png'
            },
            {
                'src': '/images/smile/tieba/40.png'
            },
            {
                'src': '/images/smile/tieba/41.png'
            },
            {
                'src': '/images/smile/tieba/42.png'
            },
            {
                'src': '/images/smile/tieba/43.png'
            },
            {
                'src': '/images/smile/tieba/44.png'
            },
            {
                'src': '/images/smile/tieba/45.png'
            }
        ]
    },
    {
        // tab 的标题
        title: 'emoji',
        // type -> 'emoji' / 'image'
        type: 'emoji',
        // content -> 数组
        content: '😀 😃 😄 😁 😆 😅 😂 😊 😇 🙂 🙃 😉 😓 😪 😴 🙄 🤔 😬 🤐'.split(/\s/)
    }



]
var $description = $('#description')
editor.customConfig.onchange = function (html) {
    // 监控变化，同步更新到 textarea
    //$description.val(html)
    $description.val(html)
}
editor.create();
// 初始化 textarea 的值
$description.val(editor.txt.html());
//var objeditor = document.getElementById("editor");
var obj = document.getElementById("descriptionP");
//alert(obj.innerText);//这样就自动解析了
obj.innerHTML = obj.innerText;//这样重新设置html代码为解析后的格式