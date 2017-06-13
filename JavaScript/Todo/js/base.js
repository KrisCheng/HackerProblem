/**
 * Created by KrisChan on 26/04/2017.
 * All right reserved.
 */
;(function() {
    'use strict';

    var $form_add_task = $('.add-task'),
        $window = $(window),
        $body = $('body'),
        $task_delete_trigger,
        $task_detail_trigger,
        $task_detail = $('.task-detail'),
        task_list = {},
        current_index,
        $update_form,
        $task_detail_content,
        $task_detail_content_input,
        $checkbox_complete,
        $msg = $('.msg'),
        $msg_content = $msg.find('.msg-content'),
        $msg_content_cancel = $msg.find('.msg-cancel'),
        $alerter = $('.alerter');

    init();

    $msg_content_cancel.on('click', function () {
        hide_msg()
    });

    function delete_alert(arg){
        if(!arg){
            console.error('alert title is required!');
        }

        var conf = {},
            $box,
            $mask,
            $title,
            $content,
            $confirm,
            $cancel,
            dfd,
            timer,
            confirmed;

        if(typeof arg === 'string'){
            conf.title = arg;
        }
        else{
            conf = $.extend(conf, arg);
        }

        dfd = $.Deferred();

        $box = $('<div>' +
            '<div class="pop-title">'+ conf.title +'</div>'+
            '<div>' +
            '<button class="confirm">Confirm</button>' +
            '<button class="cancel">Cancel</button>' +
            '</div>'+
            '</div>')
        .css({
            color:'black',
            width: 300,
            height: 150,
            background: 'white',
            position: 'fixed',
            'border-radius': 3,
            'box-shadow': '0px 2px 3px rgba(0,0,0,0.6)'
        });

        $title = $box.find('.pop-title').css({
            margin:'30px 0 20px 0',
            padding: '5px 10px',
            'text-align':'center',
            'font-weight': 900,
            'font-size': '24px'
        });

        $confirm = $box.find('.confirm').css({
            margin: '10px 10px 0px 75px',
            'line-height': '14px',
            'font-size': '14px',
            width: '70px',
            'text-align':'center',
            background: 'rgb(59, 160, 221)',
            color: 'white'
        });

        $cancel = $box.find('.cancel').css({
            margin: '10px 0 0 0',
            'text-align':'center',
            'line-height': '14px',
            'font-size': '14px',
            width: '70px',
            background: 'Gray',
            color: 'white'
        });

        $mask = $('<div></div>')
            .css({
                position: 'fixed',
                background: 'black',
                opacity:'0.5',
                top: 0,
                bottom: 0,
                left: 0,
                right: 0
            });

        timer = setInterval(function(){
            if(confirmed != undefined){
                dfd.resolve(confirmed);
                clearInterval(timer);
                dismiss_alert();
            }
        },50);

        function dismiss_alert(){
            $mask.remove();
            $box.remove();
        }

        $confirm.on('click',function(){
            confirmed = true;
        });

        $cancel.on('click',function(){
            confirmed = false;
        });

        $mask.on('click',function(){
            confirmed = false;
        });

        function adjust_box_position(){
            var window_width = $window.width(),
                window_height = $window.height(),
                box_width = $box.width(),
                box_height = $box.height(),
                move_x,
                move_y;
            move_x = (window_width - box_width)/2;
            move_y = (window_height - box_height)/2 - 100;

            $box.css({
                left: move_x,
                top: move_y
            })
        }

        $window.on('resize', function () {
            adjust_box_position();
        });

        $mask.appendTo($body);
        $box.appendTo($body);
        $window.resize();
        return dfd.promise();
    }

    $form_add_task.on('submit', function (e){
        var new_task = {};
        //禁用默认行为
        e.preventDefault();
        var $input = $(this).find('input[name=content]');
        new_task.content = $input.val();
        if(!new_task.content){
            return;
        }
        if(add_task(new_task)){
          $input.val(null);
        }
    });

    function listen_task_detail(){
        $task_detail_trigger.on('click', function(){
            var $this = $(this);
            var $item = $this.parent();
            var index = $item.data('index');
            show_task_detail(index);
        })
    }

    function show_task_detail(index){
        render_task_detail(index);
        current_index = index;
        $task_detail.show();
    }

    //监听task是否完成
    function listen_checkbox_complete(){
        $checkbox_complete.on('click', function(){
            var $this = $(this);
            var index = $this.parent().parent().data('index');
            var item = get(index);
            if(item.complete){
                update_task(index, {complete: false});
            }
            else{
                update_task(index, {complete: true});
            }
        })
    }

    function get(index){
        return store.get('task_list')[index];
    }

    function update_task(index, data){
        if(index === undefined || !task_list[index]){
            return;
        }
        task_list[index] = $.extend({}, task_list[index], data);
        refresh_task_list();
    }

    function hide_task_detail(){
        $task_detail.hide();
    }

    function render_task_detail(index){
        if(index === undefined || !task_list[index]){
            return;
        }
        var item = task_list[index];
        //初次判断
        if(item.desc === undefined){
            item.desc='';
            item.remind_date='';
        }
        var tpl  =  '<form>'+
                    '<div class="content">'+
                    item.content+
                    '</div>'+
                    '<div>'+
                    '<input class="content-modify" autofocus autocomplete="off" type="text" name="content" value="' + item.content+ '">'+
                    '</div>'+
                    '<div class="desc">'+
                    '<textarea  name="desc" class="description">'+
                    item.desc+
                    '</textarea>'+
                    '</div>'+
                    '<div class="remind">'+
                    '<p>Remind Time</p>'+
                    '<input  class="datetime" name="remind_date" type="text" value="' + item.remind_date +'">'+
                    '<button class="update" type="submit">Update</button>'+
                    '<button class="cancel" type="reset">Cancel</button>'+
                    '</div>'+
                    '</form>';

        $task_detail.html(tpl);
        $('.datetime').datetimepicker();
        $update_form = $task_detail.find('form');

        //双击变更Task名称
        $task_detail_content = $update_form.find('.content');
        $task_detail_content_input = $update_form.find('[name=content]');
        $task_detail_content.on('dblclick',function(){
            $task_detail_content_input.show();
            $task_detail_content.hide();
        });

        $update_form.on('reset', function(e){
            e.preventDefault();
            hide_task_detail();
        });

        $update_form.on('submit', function(e){
            e.preventDefault();
            var data = {};
            data.content = $(this).find('[name=content]').val();
            data.desc = $(this).find('[name=desc]').val();
            data.remind_date = $(this).find('[name=remind_date]').val();
            update_task(index,data);
            hide_task_detail();
        })
    }

    function listen_task_delete() {
        $task_delete_trigger.on('click', function () {
            var $this = $(this);
            var $item = $this.parent();
            var index = $item.data('index');
            delete_alert("Delete this task?")
                .then(function(r){
                    r ? delete_task(index) : null;
                });
        });
    }

    function add_task(new_task){
        task_list.push(new_task);
        refresh_task_list();
        return true;
    }

    function init(){
        task_list = store.get('task_list') || [];
        if(task_list.length){
            render_task_list();
        }
        task_remind_check();
        //store.clear(); //用于清除缓存
    }

    function task_remind_check(){
        var current_timestamp;
        var itl = setInterval(function(){
            for(var i = 0; i < task_list.length; i++){
                var task_timestamp,item = get(i);
                if(item === null || item.remind_date === undefined || item.informed){
                        continue;
                }
                current_timestamp = (new Date()).getTime();
                task_timestamp = (new Date(item.remind_date)).getTime();
                if((current_timestamp - task_timestamp) >= 1){
                    update_task(i, {informed :true});
                    show_msg(item.content);
                }
            }
        },300);
    }

    function show_msg(msg){
        $alerter.get(0).play();
        $msg_content.html(msg);
        $msg.show();
    }

    function hide_msg(){
        $msg.hide();
    }

    /*
    * 渲染所有Task模板
    * */
    function render_task_list(){
        var $task_list = $('.task-list');
        $task_list.html('');//清除之前遗留html
        var complete_items =[];
        for(var i = 0; i < task_list.length; i++){
            var item = task_list[i];
            if(item && item.complete){
                complete_items[i] = item;
            }
            else{
                var $task = render_task_item(item,i);
                $task_list.prepend($task);
            }
        }

        for(var j = 0; j < complete_items.length; j++){
                $task = render_task_item(complete_items[j], j);
                if(!$task) {
                    continue;
                }
                $task.addClass('completed');
                $task_list.append($task);
        }

        $task_delete_trigger = $('.action.delete');
        $task_detail_trigger = $('.action.detail');
        $checkbox_complete = $('.task-checkbox[type=checkbox]');
        listen_task_delete();
        listen_task_detail();
        listen_checkbox_complete();
    }

    function render_task_item(data, index){
        if(!data){
            return;
        }
        var list_item_tpl =
            '<div class="task-item" data-index="'+index+'">'+
            '<span><input class="task-checkbox" '+ (data.complete ? 'checked' : '') +' type="checkbox"></span>'+
            '<span class="task-content">'+ data.content+'</span>'+
            '<span class="action delete">Delete</span>'+
            '<span class="action detail">Detail</span>'+
            '</div>';
        return $(list_item_tpl);
    }

    function  refresh_task_list(){
        store.set('task_list', task_list);
        render_task_list();
    }

    function delete_task(index){
        if(index === undefined || !task_list[index]){
            return;
        }
        delete task_list[index];
        refresh_task_list();
    }

})();