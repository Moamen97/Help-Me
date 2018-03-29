/*
package Model

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import Model.postData.*
import android.content.Intent
import android.support.annotation.NonNull
import android.widget.Toast
import com.helpme.home


class Functionalities(var context: Context) {
    private var dataBaseInstance = FirebaseFirestore.getInstance();
    fun signUp(newUser: user) {

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

        dataBaseInstance.collection("user")
                .whereEqualTo("userName", newUser.getUserName())
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        println("Entered Complete Listener");
                        if (p0.isSuccessful) {
                            if (p0.result.isEmpty) {
                                // getting empty documents ; here we should push our user to db :V
                                println("No Document Data");
                                var userData = HashMap<String, Any>();
                                userData.put("userName", newUser.userName);
                                userData.put("image", newUser.imageID);
                                userData.put("password", newUser.password);
                                userData.put("eMail", newUser.email);
                                userData.put("firstName", newUser.firstName);
                                userData.put("midName", newUser.midName);
                                userData.put("lastName", newUser.lastName);
                                userData.put("gender", newUser.gender);
                                userData.put("phoneNum", newUser.phoneNum);
                                userData.put("behav_rate", newUser.behaveRate);
                                userData.put("birthDate", newUser.birthDate);
                                dataBaseInstance.collection("user").document(newUser.userName).set(userData);
                                Toast.makeText(context, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // here i found someone has the same userName :V so i can't add this user
                                println(p0.result.documents[0].data.toString());
                                Toast.makeText(context, "Sign up is not success user name is already exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Task is not Successfull ,, should be throw an exception
                            println(p0.exception.toString());
                        }
                    }
                })
    }
    fun signIn(userName: String, password: String) {
        dataBaseInstance.collection("user")
                .whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(p0: Task<QuerySnapshot>) {
                        if (p0.isSuccessful) {
                            if (!p0.result.isEmpty) {
                                println(p0.result.documents[0].data.toString())
                                if (p0.result.documents[0].get("password").equals(password)) {
                                    Common.currentUser = p0.result.documents[0].toObject(user::class.java);
                                    Toast.makeText(context, "Sign In Successfully " + Common.currentUser.getFirstName() + " " + Common.currentUser.getMidName() + " " + Common.currentUser.getLastName() + " ", Toast.LENGTH_LONG).show();
                                    val homeIntent = Intent(context, home::class.java);
                                    context.startActivity(homeIntent);

                                } else {
                                    Toast.makeText(context, "login failed:wrong username or password", Toast.LENGTH_SHORT).show()

                                }
                            } else {
                                println("No Document Data");
                                Toast.makeText(context, "login failed:you are offline", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            println(p0.exception.toString());
                        }
                    }
                })
    }

    fun addPost(Post: post) {

    }
}*/