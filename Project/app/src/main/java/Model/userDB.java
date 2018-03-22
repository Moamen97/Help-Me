package Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import Interface.userQueries;

import static android.content.ContentValues.TAG;


public class userDB implements userQueries {

    public static Boolean status = false;
    private FirebaseFirestore db;
    private user ourUser = null;
    private Context context;

    public userDB(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void addUser(final user newUser) {
        db.collection("user")
                .whereEqualTo("userName", newUser.getUserName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                System.out.println(String.valueOf(task.getResult().getDocuments().get(0).getData()));
                                Toast.makeText(context, "Sign up is not success user name is already exist", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println(String.valueOf("No Document Data"));
                                db.collection("user").document(newUser.getUserName()).set(newUser);
                                Toast.makeText(context, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public user getUserByName(String Name) {
        return null;
    }

    @Override
    public user getUserByUserName(String userName) {

        return null;
    }

    @Override
    public void getUserByUserNameAndChecksPassword(String userName, final String password) {
        db.collection("user")
                .whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                System.out.println(String.valueOf(task.getResult().getDocuments().get(0).getData()));
                                if (task.getResult().getDocuments().get(0).get("password").equals(password)) {
                                    Toast.makeText(context, "Sign In Successfully ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Wrong user name of password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                System.out.println(String.valueOf("No Document Data"));
                                Toast.makeText(context, "Wrong user name of password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public user getUserByEmail(String Email) {
        return null;
    }
}
