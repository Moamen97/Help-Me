package Model;

import android.content.Context;
import android.content.Intent;
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
import com.helpme.home;

import java.util.HashMap;
import java.util.Map;

import Common.Common;
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
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("userName", newUser.getUserName());
                                userData.put("image", newUser.getImage());
                                userData.put("password", newUser.getPassword());
                                userData.put("eMail", newUser.geteMail());
                                userData.put("firstName", newUser.getFirstName());
                                userData.put("midName", newUser.getMidName());
                                userData.put("lastName", newUser.getLastName());
                                userData.put("gender", newUser.getGender());
                                userData.put("phoneNum", newUser.getPhoneNum());
                                userData.put("behav_rate", newUser.getBehav_rate());
                                userData.put("birthDate", newUser.getBirthDate());
                                // put the values of user to their document
                                db.collection("user").document(newUser.getUserName()).set(userData);
                                // try to put their collections
                                CollectionReference collectionReference = db.collection("user").document(newUser.getUserName()).collection("notifications");
                                // set notifications
                                if (newUser.getNotificationsList() != null) {
                                    for (int i = 0; i < newUser.getNotificationsList().size(); ++i) {
                                        DocumentReference notificationReference = collectionReference.document();
                                        String myId = notificationReference.getId();
                                        Map<String, Object> notificationData = new HashMap<>();
                                        notificationData.put("notificationData", newUser.getNotificationsList().get(i).getNotificationData());
                                        notificationData.put("notificationTime", newUser.getNotificationsList().get(i).getNotificationTime());
                                        notificationReference.set(notificationData);
                                    }
                                }
                                collectionReference = db.collection("user").document(newUser.getUserName()).collection("posts");
                                // set notifications
                                if (newUser.getPostsList() != null) {
                                    for (int i = 0; i < newUser.getPostsList().size(); ++i) {
                                        DocumentReference postReference = collectionReference.document();
                                        String myId = postReference.getId();
                                        Map<String, Object> postData = new HashMap<>();
                                        postData.put("comments", newUser.getPostsList().get(i).getCommentList());
                                        postData.put("followers", newUser.getPostsList().get(i).getFollowerList());
                                        postData.put("image", newUser.getPostsList().get(i).getImage());
                                        postData.put("postContent", newUser.getPostsList().get(i).getPostContent());
                                        postData.put("postTime", newUser.getPostsList().get(i).getPostTime());
                                        postReference.set(postData);
                                    }
                                }
                                collectionReference = db.collection("user").document(newUser.getUserName()).collection("profession");
                                // set notifications
                                if (newUser.getUserProfession() != null) {
                                    DocumentReference professionRef = collectionReference.document();
                                    String myId = professionRef.getId();
                                    Map<String, Object> professionData = new HashMap<>();
                                    professionData.put("isProfessional", newUser.getUserProfession().getProfessional());
                                    professionData.put("professionCategory", newUser.getUserProfession().getProfessionCategory());
                                    professionData.put("professionalRate", newUser.getUserProfession().getProfessionalRate());
                                    professionRef.set(professionData);
                                    CollectionReference workShopRef = db.collection("user").document(newUser.getUserName()).
                                            collection("profession").document(myId).collection("workshop");
                                    DocumentReference workshopRef = collectionReference.document();
                                    String workShopID = workShopRef.getId();
                                    if (newUser.getUserProfession().getWorkshopsList() != null) {
                                        for (int i = 0; i < newUser.getUserProfession().getWorkshopsList().size(); ++i) {
                                            Map<String, Object> workshopData = new HashMap<>();
                                            workshopData.put("workingHours", newUser.getUserProfession().getWorkshopsList().get(i).getWorkingHours());
                                            workshopData.put("workshopRate", newUser.getUserProfession().getWorkshopsList().get(i).getWorkshopRate());
                                            workshopData.put("workshopPhoneNum", newUser.getUserProfession().getWorkshopsList().get(i).getWorkshopPhoneNum());
                                            workShopRef.document().set(workshopData);
                                        }
                                    }
                                }
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
                                    Common.currentUser = task.getResult().getDocuments().get(0).toObject(user.class);
                                    Toast.makeText(context, "Sign In Successfully " + Common.currentUser.getFirstName() + " " + Common.currentUser.getMidName() + " " + Common.currentUser.getLastName() + " ", Toast.LENGTH_LONG).show();
                                    Intent homeIntent = new Intent(context, home.class);
                                    context.startActivity(homeIntent);
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
