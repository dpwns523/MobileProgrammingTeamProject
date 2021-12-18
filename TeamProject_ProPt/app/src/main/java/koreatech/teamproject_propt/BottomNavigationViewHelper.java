package koreatech.teamproject_propt;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewHelper {

    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationMyProfile:
                          Intent intent1 = new Intent(context, user_info.class);  // 0
                          context.startActivity(intent1);
                        break;
                    case R.id.navigationHome:
                        Intent intent3 = new Intent(context, HomeActivity.class); // 2
                        context.startActivity(intent3);
                        break;
                    case R.id.navigationMenu:
                        break;
                }
                return false;
            }
        });
    }
}

