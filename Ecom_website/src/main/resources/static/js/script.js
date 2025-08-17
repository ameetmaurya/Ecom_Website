$(function(){
//User Registration Validation
var $userRegister=$("#userRegister");
  $userRegister.validate({

rules:{
name:{
  required:true,
  lettersonly:true
},
email:{
   required:true,
   space:true,
   email:true
},
mobileNumber:{
required:true,
space:true,
numericOnly:true,
minlength:10,
maxlength:12
},

password:{
required:true,
space:true,
},

confirmpassword:{
required:true,
space:true,
equalTo:'#pass'
},

address:{
 required:true,
 all:true
},
city:{
required:true,
space:true
},
state:{
required:true,
space:true
},

pincode:{
required:true,
space:true,
numericOnly:true
},
img:{
required:true
}


},
messages:{
  name:{
  required:'Name required',
  lettersonly:'invalid name'
  },
  email:{
  required:'email must be required',
  space:'space not allowed',
  email:'invalid email'
  },
  mobileNumber:{
  required:'mobile No must be required',
    space:'space not allowed',
    email:'invalid Mob.no',
    minlength:'min 10 digit',
    maxlength:'min 12 digit'
  },

  password:{
  required:'password must be required',
  space:'space not allowed'
  },
  confirmpassword:{
   required:' confirm password must be required',
    space:'space not allowed',
    equalTo:'password mismatch'
  },
  address:{
  required:' Address must be required',
  all:'invalid'
  },
  city:{
   required:' City must be required',
   space:'space not allowed'
  },
  state:{
  required:' state must be required',
   space:'space not allowed'
  },
  pincode:{
   required:' pincode must be required',
     space:'space not allowed',
     numericOnly:'invalid pincode'
  },
  img:{
  required:'image required'
  }

}

  });

  //Reset password validation
    var $resetPassword=$("#resetPassword");
    $resetPassword.validate({

    rules:{
    password:{
    required:true,
    space:true,
    },

    confirmPassword:{
    required:true,
    space:true,
    equalTo:'#password'
    },
    },
    messages:{
     password:{
      required:'password must be required',
      space:'space not allowed'
      },
      confirmPassword:{
       required:' confirm password must be required',
        space:'space not allowed',
        equalTo:'password mismatch'
      }
      }
    });

//Orders validation
 var $orders=$("#orders");
    $orders.validate({

    rules:{
   firstName:{
     required:true,
     lettersonly:true
   },
    LastName:{
        required:true,
        lettersonly:true
      },
   email:{
      required:true,
      space:true,
      email:true
   },
   mobileNo:{
   required:true,
   space:true,
   numericOnly:true,
   minlength:10,
   maxlength:12
   },
   address:{
    required:true,
    all:true
   },
   city:{
   required:true,
   space:true
   },
   state:{
   required:true,
   space:true
   },

   pincode:{
   required:true,
   space:true,
   numericOnly:true
   },
   paymentType:{
   required:true
   }

    },
   messages:{
   firstName:{
     required:'First Name required',
     lettersonly:'invalid name'
     },
     LastName:{
          required:'Last Name required',
          lettersonly:'invalid name'
          },
     email:{
     required:'email must be required',
     space:'space not allowed',
     email:'invalid email'
     },
     mobileNo:{
     required:'mobile No must be required',
       space:'space not allowed',
       email:'invalid Mob.no',
       minlength:'min 10 digit',
       maxlength:'min 12 digit'
     },

   address:{
  required:' Address must be required',
  all:'invalid'
  },
  city:{
   required:' City must be required',
   space:'space not allowed'
  },
  state:{
  required:' state must be required',
   space:'space not allowed'
  },
  pincode:{
   required:' pincode must be required',
     space:'space not allowed',
     numericOnly:'invalid pincode'
  },
   paymentType:{
     required:'Please Select Payment Method'
     }
      }

    })
})




jQuery.validator.addMethod('lettersonly',function(value,element){
return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
})


jQuery.validator.addMethod('space',function(value,element){
return /^[^-\s]+$/.test(value);
})


jQuery.validator.addMethod('all',function(value,element){
return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
})

jQuery.validator.addMethod('numericOnly',function(value,element){
return /^[0-9]+$/.test(value);
})
