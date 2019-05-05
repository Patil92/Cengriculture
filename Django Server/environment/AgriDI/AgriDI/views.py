
from django.shortcuts import render
from django.http import HttpResponse
from django.views.static import serve

# Create your views here.
import numpy as np
from sklearn.externals import joblib
import pickle
#import urllib2
import simplejson
from django.http import JsonResponse 
import base64
import os
import pyrebase
#import numpy as np

config = {
    'apiKey': "AIzaSyDD4DOfXlxxxxxxxxxxxxxxxx",
    'authDomain': "xxxxxxxxxxxxxxxxxxxxx",
    'databaseURL': "xxxxxxxxxxxxxxxxxxxxx",
    'projectId': "xxxxxxxxxxxxxxxxxxxxx",
    'storageBucket': "xxxxxxxxxxxxxxxxxxxxx",
    'messagingSenderId': "3xxxxxxxxxxxxxxxxxxxxx4"
  };

firebase = pyrebase.initialize_app(config)
#auth = firebase.auth() 

#user = auth.sign_in_with_email_and_password(email, password)
db = firebase.database()

def getData(self):
    s=db.child("Weather").child("Mysore").child("temp").get().val()
    res={
        "data":s
    }
    return JsonResponse(res)
    
    
# python3 -m pip install numpy

def Predict1(data):
    
    data=np.array(data)
    clf=joblib.load("/home/ec2-user/environment/Agri/model.pkl","rb")
    pred=clf.predict(data)
    res=int(pred)
    print('Result : ',res)
    return res

def sendimg(self):
    
    res1="/root/Patil92/tomato.jpg"
    res2="/home/ec2-user/environment/AgriDI/AgriDI/images/gradient2.png"
    res="tomato.jpg"
    
    image_data = open(res2, "rb").read()
    return HttpResponse(image_data, content_type="image/png")
    

k=['Barley','Rice','Tomato','Wheat','Chilli']   
sujns='''We recommend you to grow the above crop inorder to meet the demand and supply ratio in the society. It is advised to divide the  total areas as predicted to increase the yield and income.'''
         
def Predict(request):
    
    #http://13.251.233.43/predict?acres=92&plant=tomato&city=Mysore
    #http://13.251.233.43/predict?acres=92&plant=Rice&city=Gulbarga
    acres=request.GET["acres"]
    plant=request.GET["plant"]
    city=request.GET["city"]
    
    temp=db.child("Weather").child(city).child("temp").get().val()
    humy=db.child("Weather").child(city).child("humy").get().val()
    city=db.child("Weather").child(city).child("moisture").get().val()
    #db.child("Weather").child(city).child("rainfall").get().val()
    rainfall="120"
    
    l=[] 
    '''r={
        "a":temp,
        "b":humy,
        "c":city,
        "d":rainfall
    }
    
    return JsonResponse(r)
    '''
    l.append(int(temp))
    l.append(int(humy))
    l.append(int(city))
    l.append(int(rainfall))

    l=[l]
    data=np.array(l)
    
    clf=joblib.load("/home/ec2-user/environment/Agri/model.pkl","rb")
    pred=clf.predict(data)
    r=int(pred)
    
    
    print('Result : ',r)
    
    res={
        "res":r,
        "Plant":k[r],
        "Sugessions": sujns
        
    }
    
    return JsonResponse(res)
    
    #return HttpResponse("<h1> Welcomes To Patil's World </h1> <br> " + str(data))

def Predict2(data):
    data=np.array(data)
    f= open("/home/ec2-user/environment/Agri/model.pkl","rb")
    text = f.read()
    
    with open("model.pkl", 'rb') as f:
            model = pickle.load(f)
            
    res=model.predict(data)
    
    #model = pickle.load(text)
 
    
    return res
    


def index(request):
    
    temp=request.GET["temp"]
    humy=request.GET["humy"]
    
    res=Predict1([[1,130,30,317]])
    
    return HttpResponse("<h1> Welcomes To Patil's World </h1> <br> " + str(temp) +"<br>" + str(humy) +"\n"+str(res))