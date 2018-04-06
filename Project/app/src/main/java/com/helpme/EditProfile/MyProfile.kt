package com.helpme.EditProfile

import Common.mySelf
import Control.UserControl
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.helpme.R

class MyProfile : AppCompatActivity() {

    val UserController: UserControl = UserControl.getInstance(null, null,
            null, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val firstname = mySelf.get_firstName()
        val midname = mySelf.get_midName()
        val lastname = mySelf.get_lastName()
        val mail = mySelf.get_email()
        val Number = mySelf.get_phoneNum()
        val BirthDate = mySelf.get_birthDate()
        val gender = mySelf.get_gender()
        val pass = mySelf.get_password()


        val editMyProfile = findViewById<FloatingActionButton>(R.id.fab)


        val dialog: Dialog = Dialog(this)
        dialog.setContentView(R.layout.edit_profile_fragment)
        editMyProfile.setOnClickListener {
            /*     dialog.setContentView(R.layout.add_post);
                 EditText postContent = dialog.findViewById(R.id.postContent);
                 Button postButton = dialog.findViewById(R.id.postButton);
                 Button plumberButton = dialog.findViewById(R.id.plumberButton);
                 Button carpenterButton = dialog.findViewById(R.id.carpenterButton);
                 Button mechanicButton = dialog.findViewById(R.id.mechanicButton);
                 Button engineerButton = dialog.findViewById(R.id.engineerButton);
                 Button cookingButton = dialog.findViewById(R.id.cookingButton);
                 Button doctorButton = dialog.findViewById(R.id.doctorButton);
                 TextView postTypeTextView = dialog.findViewById(R.id.postTypeTextView);
                 TextView postContentTextView = dialog.findViewById(R.id.postContentTextView);
                 postTypeTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Nabila.ttf"));
                 postContentTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Nabila.ttf"));
                 buttonsEffect(cookingButton, plumberButton, carpenterButton, mechanicButton, engineerButton, doctorButton, postButton, postContent, dialog);
                 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 dialog.show();*/
            val firstnameView = dialog.findViewById<TextView>(R.id.editFirstNametextbox)
            val midnameView = dialog.findViewById<TextView>(R.id.editMidNametextbox)
            val lastnameView = dialog.findViewById<TextView>(R.id.editLastNametextbox)
            val genderView = dialog.findViewById<TextView>(R.id.editGendertextbox)
            val emailView = dialog.findViewById<TextView>(R.id.editEmailtextbox)
            val passView = dialog.findViewById<TextView>(R.id.editPassWordtextbox)
            val phonenumView = dialog.findViewById<TextView>(R.id.editPhonetextbox)
            val birthdateView = dialog.findViewById<TextView>(R.id.editBirthDatetextbox)
            val buttonSave = dialog.findViewById<Button>(R.id.SaveInfo)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            buttonSave.setOnClickListener {
                editMyProfile(dialog)
            }
        }

    }

    fun btnEditWorkshopInfo(view: View) {
        var intent = Intent(this, EditWorkshop::class.java)
        startActivity(intent)
    }

    fun editMyProfile(dialog: Dialog) {
        var newFirstName: String = (dialog.findViewById<(EditText)>(R.id.editFirstNametextbox)).text.toString();
        var newMidName: String = (dialog.findViewById<(EditText)>(R.id.editMidNametextbox)).text.toString();
        var newLastName: String = (dialog.findViewById<(EditText)>(R.id.editLastNametextbox)).text.toString();
        var newGender: String = (dialog.findViewById<(EditText)>(R.id.editGendertextbox)).text.toString();
        var newemail: String = (dialog.findViewById<(EditText)>(R.id.editEmailtextbox)).text.toString();
        var newpassword: String = (dialog.findViewById<(EditText)>(R.id.editPassWordtextbox)).text.toString();
        var newmobile: String = (dialog.findViewById<(EditText)>(R.id.editPhonetextbox)).text.toString();
        var newBirthDate: String = (dialog.findViewById<(EditText)>(R.id.editBirthDatetextbox)).text.toString();


        if (newemail == "") {
            this.ShowToast("Email Cannot Be Empty")
            return
        }
        if (newpassword == "") {
            this.ShowToast("Password Cannot Be Empty")
            return
        }
        if (newBirthDate != "" && newBirthDate[2] != '/' && newBirthDate[5] != '/') {
            this.ShowToast("Birth Date should be like dd/mm/yyyy")
            return
        }
        UserController.UpdateUserInfo(newFirstName, newMidName, newLastName, newGender, newemail, newpassword, newmobile, newBirthDate)
        dialog.dismiss()
     }

    fun btnSaveChanges(view: View) {

    }

    fun ShowToast(Msg: String) {
        Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
    }

}
