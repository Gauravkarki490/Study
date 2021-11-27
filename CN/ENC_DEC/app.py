from flask import Flask, render_template,request,redirect,flash,send_file,session, sessions,url_for,send_from_directory
from Crypto.Cipher import DES3
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
import os
import math
import random
import smtplib
from io import BytesIO
import mysql.connector
from hashlib import md5
from mysql.connector import optionfiles
from werkzeug.utils import secure_filename
from werkzeug.wrappers import response
from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)
otp=""

app.secret_key=os.getenv("secret_key")
Host=os.getenv("Host")
MySqlUser=os.getenv("MySqlUser")
MYSqlPassword=os.getenv("MySqlPassword")
SysmMail=os.getenv("SysmMail") 
SysmMailPas=os.getenv("SysmMailPas")

imgfolder=os.path.join('static','img')
dow=os.path.join('static','file')




app.config['UPLOAD_FOLDER']=imgfolder
img1=os.path.join(app.config['UPLOAD_FOLDER'],'img_avatar2.png')

mydb = mysql.connector.connect(host=Host,user=MySqlUser,passwd=MYSqlPassword,database="User")
mydb2 = mysql.connector.connect(host=Host,user=MySqlUser,passwd=MYSqlPassword,database="admin")
mydb3 = mysql.connector.connect(host=Host,user=MySqlUser,passwd=MYSqlPassword,database="cndata")
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])


@app.after_request
def after_request(response):
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate,post-check=0,pre-check=0"
    return response



@app.route("/signin",methods=["GET","POST"])
def signin():
    if request.method=="POST":
        name=request.form['username']
        pas=request.form['password']
        pas2=request.form['password2']
        ph=request.form['phone']
        mail=request.form['email']
        mycur=mydb.cursor()
        mycur.execute( "Select * from users")
        myres=mycur.fetchall()
        for r in myres:
            if mail==r[1]:
                flash("User Already Exist","danger")
                return redirect(url_for("signin"))


        if len(mail)==0:
            flash('Email is Empty.',"danger")
            redirect(url_for("signin"))
        elif len(mail) <12:
            flash('Email is invalid.',"danger")
            redirect(url_for("signin"))
        elif len(name) <3:
             flash('Name len is invalid', "danger")
             redirect(url_for("signin"))
        elif pas!=pas2:
             flash('Password don\'t match.', "danger")
             redirect(url_for("signin"))
        elif len(pas) <7:
             flash('Password len should be greater then 7', "danger")
             redirect(url_for("signin"))
        else:
            #Account Verification
            details=[mail,name,pas,ph]
            session["details"]=details  
            return redirect(url_for("AccVerificationOTP",email=mail))
             
            
        
    return render_template("Signin.html")




#Account Verification

@app.route("/AccVerificationOTP/<email>")
def AccVerificationOTP(email):
    s = smtplib.SMTP("smtp.gmail.com" , 587)  
    # start TLS for E-mail security 
    s.starttls()
    # Log in to your gmail account
    s.login(SysmMail , SysmMailPas)
    otp = random.randint(100000, 999999)
    otp = str(otp)
    # mail=session.get("user")
    msg="Account Verification code -"
    s.sendmail(SysmMail,email, msg+otp)
    print("OTP sent succesfully..")
    session["ACCOTP"]=otp
    
   
    
    s.quit()
    return redirect(url_for("ACCVerification"))





@app.route("/ACCVerification", methods=["POST","GET"])
def ACCVerification():
    
    otpS=session.get("ACCOTP")
    print(otpS)
    if request.method == 'POST':
        otpS=session.get("ACCOTP")
        session.pop("ACCOTP",None)
        otpR=request.form['otp']
        
        if otpS == otpR :
            details=session.get("details")
            session.pop("details",None)
            mycur=mydb.cursor()
            # mycur.execute( "INSERT INTO USERS(email,name,password,phoneno)" "VALUES (%s, %s,%s,%s)",(email,name,pas,ph))
            mycur.execute( "INSERT INTO USERS(email,name,password,phoneno)" "VALUES (%s, %s,%s,%s)",(details[0],details[1],details[2],details[3]))
            mydb.commit()
            flash('You are now  Registered!', "success")
            return redirect(url_for("Userlogin"))
        else:
            flash("Wrong OTP!","danger")
    return render_template("AccVerification.html")
    















@app.route("/")
def home():
    return render_template('index.html')


@app.route("/Userlogin", methods = ['GET','POST'])
def Userlogin():
    if request.method=="POST":
        mail=request.form['umail']
        pas=request.form['psw']
        mycur=mydb.cursor()
        mycur.execute("SELECT * from users")
        res=mycur.fetchall()
        
        for i in res:
            if i[1] == mail and i[3] == pas:
                session['user']=mail
                return redirect(url_for("OTP",email=mail))
                
        
        flash("Wrong Credentials!","danger")
        return redirect(url_for("Userlogin"))
    else:
        if "user" in session:
            redirect(url_for("UserPage"))
    return render_template('Ulogin.html',user_img = img1)

@app.route("/OTP/<email>")
def OTP(email):
    s = smtplib.SMTP("smtp.gmail.com" , 587)  
    # start TLS for E-mail security 
    s.starttls()
    # Log in to your gmail account
    s.login(SysmMail , SysmMailPas)
    otp = random.randint(100000, 999999)
    otp = str(otp)
    mail=session.get("user")
    msg="OTP for Verification-"
    s.sendmail(SysmMail,mail, msg+otp)
    print("OTP sent succesfully..")
  
    session["OTP"]=otp

    
    s.quit()
    return redirect(url_for("OTPVerification"))

@app.route("/OTPVerification", methods = ['GET','POST'])
def OTPVerification():
            
            otpS=session.get("OTP")
            print(otpS)
            if request.method == 'POST':
                otpS=session.get("OTP")
                session.pop("OTP",None)
                otpR=request.form['otp']
                
                if otpS == otpR or otp==otpR:
                    flash("Welcome User!","success")
                    return redirect(url_for("UserPage"))
                else:
                    flash("Wrong OTP!","danger")
            return render_template("OtpVerification.html")
    
   


@app.route("/AdminPage", methods = ['GET','POST'])
def AdminPage():
    if 'Admin' in session:
        if request.method == 'POST':
    
            title=request.form['title']
            file=request.files['Files']
            data=file.read()
            filename= secure_filename(file.filename)
            
            key=request.form.get('key')
            key_hash=md5(key.encode('ascii')).digest()
            tdes_key=DES3.adjust_key_parity(key_hash)
            cipher=DES3.new(tdes_key, DES3.MODE_EAX ,nonce=b'0')
            new_file_bytes=cipher.encrypt(data)
            mycur=mydb3.cursor()
            mycur.execute( "INSERT INTO files(title,Enckey,files_name,files)" "VALUES (%s, %s,%s,%s)",(title,key,filename,new_file_bytes))
            mydb3.commit()
            flash("Data is added!","success")
            return  redirect(url_for("AdminPage"))
        mycur=mydb3.cursor()
        mycur.execute("SELECT * FROM files")
        allres=mycur.fetchall()
       
        return render_template('AdminPage.html', allres=allres)
    else:
        
        return redirect(url_for("home"))




@app.route("/UserPage", methods = ['GET','POST'])
def UserPage():
  
        

    if 'user' in session:
        mycur=mydb3.cursor()
        mycur.execute("SELECT * FROM files")
        allres=mycur.fetchall()
        
        if "ID" in session:
            idn=session.get("ID")
            session.pop("ID",None)
            return render_template('UserPage.html',allres=allres,id=idn)
        return render_template('UserPage.html',allres=allres)
    else:
        return redirect(url_for("home"))




@app.route("/download/<idno>")
def download(idno):
        mycur=mydb3.cursor()
        mycur.execute("Select title,Enckey,files_name,files from files where id=%s",[idno])
        myres=mycur.fetchone()
        title=myres[0]
        key=myres[1]
        file_name=myres[2]
        data=myres[3]
        key_hash=md5(key.encode('ascii')).digest()
        tdes_key=DES3.adjust_key_parity(key_hash)
        cipher=DES3.new(tdes_key, DES3.MODE_EAX ,nonce=b'0')
        new_file_bytes=cipher.decrypt(data)

        
        
      
        
      
        return send_file(BytesIO(new_file_bytes), download_name=file_name, as_attachment=True)
       


@app.route("/KeyVerification", methods = ['GET','POST'])
def KeyVerification():
    if request.method == 'POST':
        keyS=session.get("KEY")
        session.pop("KEY")
        keyR=request.form["key"]
        if keyS == keyR:
            id=session.get("ID")
            # session.pop("ID")
            # return redirect(url_for("download",idno=id))
            return redirect(url_for("UserPage"))
        flash("Wrong Key!","danger")


    return render_template("KeyVerification.html") 

@app.route("/KeySend/<int:id>")
def KeySend(id):
    s = smtplib.SMTP("smtp.gmail.com" , 587)  
    # start TLS for E-mail security 
    s.starttls()
    # Log in to your gmail account
    
    
    s.login(SysmMail , SysmMailPas)

    mycur=mydb3.cursor()
    mycur.execute("Select title,Enckey,files_name,files from files where id=%s",[id])
    myres=mycur.fetchone()
    
    key=myres[1]
  
    session["KEY"]=key
    session["ID"]=id
    mail=session.get("user")
    # s.sendmail("enc.dec.3des@gmail.com","karkigaurav00@gmail.com", key)
    print(mail)
    msg="Key for Encryption-"
    s.sendmail(SysmMail,mail, msg+key)
    print("Key sent succesfully..")

    
    s.quit()
    return redirect(url_for("KeyVerification"))




@app.route("/Adminlog", methods = ['GET','POST'])
def Adminlogin():
    if request.method=="POST":
        
        mail=request.form['umail']
        pas=request.form['psw']
        
        mycur=mydb2.cursor()
        mycur.execute( "SELECT * from admins")
        res=mycur.fetchall()
        session["Admin"]=mail
        for i in res:
            if i[0] == mail and i[1] == pas:
                flash("Welcome Admin!","success")
                return redirect(url_for("AdminPage"))
        flash("Wrong Credentials!","danger")
        return redirect(url_for("Adminlogin"))
        
        
    return render_template('Adminlog.html',user_img = img1)

@app.route("/delete/<int:id>")
def delete(id):
    mycur=mydb3.cursor()
    mycur.execute("Delete from files where id=%s",[id])
    mydb3.commit()
    mycur.execute("SELECT * FROM files")
    allres=mycur.fetchall()
  
    flash("Succefully deleted!","success")
    return  redirect(url_for("AdminPage"))


@app.route("/Userlogout")
def Userlogout():
    if 'user' in session:
        use=session["user"]
   
    session.clear()
    flash(f"{use} have been logout","success")
    return redirect(url_for("home"))

@app.route("/Adminlogout")
def Adminlogout():
    session.clear()
    flash("You have been logout","success")
    return redirect(url_for("home"))




if __name__ =="__main__":
    
    app.run(debug=True)