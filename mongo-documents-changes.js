
//Questions document
db.getCollection("questions").find({})

db.getCollection("questions").insert(
   {
      "question": "Which is false about string concatenation? (Only one answer is right) 1",
      "answer1": " 'A' + 'B' +  // Creates two instances of the StringBuilder object to execute this line of code",
      "answer2": "A method with a final identifier cannot be overridden",
      "answer3": "Final objects cannot have internal properties values being changed",
      "answer4": "Final variables cannot have a new reference after declared",
      "answerCorrect": "1",
      "quizType": "java"
   });
   
   
db.getCollection("questions").updateMany(
	{},
    {	
        $set : {
			"quizType": "java"
    	}
    }
);



//User document
db.getCollection("users").find({})

db.getCollection("users").insert(
   {
      "name": "REACTZAO",
      "surName": "REACTZAO",
      "email": "REACTZAO@REACTZAO.COM",
      "phone": "",
      "company": "",
      "rightAnswer": "",
      "startDateTime": "2019-05-28 13:02:18.054Z",
      "endDateTime": "2019-05-28 13:03:27.769Z",
      "userAnswerCorrect": "2",
      "userAnswers": "4",
      "timeTest": "69715",
      "timeDisplayGrid": "1 m: 69 s",
      "quizType":"react" 
       
   });
   

   
   db.getCollection("users").updateMany(
      {},
       {	
           $set : {
            "quizType": "java"
          }
       }
   );
   
//Admin Document
//Insert username and password
   
   db.admin.insert({
	      "username" : "...",
	      "password" : "..."});


