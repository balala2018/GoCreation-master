package gc.gocreation.com.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by 包子 on 2018/4/5
 * Function
 * 为所有的Fragment提供公共的行为或事件
 */

public class BaseFragment extends Fragment {
    protected Activity mContext;

    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }




}
