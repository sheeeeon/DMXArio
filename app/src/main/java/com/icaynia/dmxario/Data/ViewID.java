package com.icaynia.dmxario.Data;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2016. 12. 15..
 */

public class ViewID {
    public Controller controller = new Controller();
    public SceneID sceneId = new SceneID();
    public ViewID () {

    }
    public class Controller {
        public int[] position = {
                R.id.pos_1, R.id.pos_2, R.id.pos_3, R.id.pos_4, R.id.pos_5, R.id.pos_6, R.id.pos_7, R.id.pos_8, R.id.pos_9, R.id.pos_10, R.id.pos_11, R.id.pos_12,
                R.id.pos_13, R.id.pos_14, R.id.pos_15, R.id.pos_16, R.id.pos_17, R.id.pos_18, R.id.pos_19, R.id.pos_20, R.id.pos_21, R.id.pos_22,R.id.pos_23,R.id.pos_24,
                R.id.pos_25, R.id.pos_26, R.id.pos_27, R.id.pos_28, R.id.pos_29, R.id.pos_30, R.id.pos_31, R.id.pos_32, R.id.pos_33, R.id.pos_34, R.id.pos_35, R.id.pos_36,
                R.id.pos_37, R.id.pos_38, R.id.pos_39, R.id.pos_40, R.id.pos_41, R.id.pos_42, R.id.pos_43, R.id.pos_44, R.id.pos_45, R.id.pos_46, R.id.pos_47, R.id.pos_48,
        };

        public int[] seekbar = {
                R.id.seekbar1, R.id.seekbar2, R.id.seekbar3, R.id.seekbar4, R.id.seekbar5, R.id.seekbar6, R.id.seekbar7, R.id.seekbar8
        };

        public int[] seekbarSelectChannelButton = {
            R.id.channel_select1, R.id.channel_select2, R.id.channel_select3, R.id.channel_select4
        };

        public int recordButton = R.id.recBt;
        public int editButton = R.id.editBt;
        public int prevFrameButton = R.id.prev_frame;
        public int nextFrameButton = R.id.next_frame;

        public int seekbar_customizing = R.id.seekbar_customize;

        public int button_framereset = R.id.button_framereset;
        public int button_allreset = R.id.button_allreset;
        public int button_save = R.id.button_save;
        public int button_load = R.id.button_load;
    }

    public class SceneID {
        public int[] scene = {
                R.id.r1, R.id.r2, R.id.r3, R.id.r4, R.id.r5, R.id.r6, R.id.r7, R.id.r8, R.id.r9, R.id.r10, R.id.r11, R.id.r12,

                R.id.r13, R.id.r14, R.id.r15, R.id.r16, R.id.r17, R.id.r18, R.id.r19, R.id.r20, R.id.r21, R.id.r22, R.id.r23, R.id.r24,

                R.id.r25, R.id.r26, R.id.r27, R.id.r28, R.id.r29, R.id.r30, R.id.r31, R.id.r32, R.id.r33, R.id.r34, R.id.r35, R.id.r36,

                R.id.r37, R.id.r38, R.id.r39, R.id.r40, R.id.r41, R.id.r42, R.id.r43, R.id.r44, R.id.r45, R.id.r46, R.id.r47, R.id.r48,

                R.id.r49, R.id.r50, R.id.r51, R.id.r52, R.id.r53, R.id.r54, R.id.r55, R.id.r56, R.id.r57, R.id.r58, R.id.r59, R.id.r60,

                R.id.r61, R.id.r62, R.id.r63, R.id.r64, R.id.r65, R.id.r66, R.id.r67, R.id.r68, R.id.r69, R.id.r70, R.id.r71, R.id.r72
        };
    }
}
