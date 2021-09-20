const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
exports.addToQueue = functions.https.onCall((data, context) => {
  const uid = context.auth.uid;
  return admin.database().ref("/queue/players").child(uid).set({
    uid: uid,
  }).then(() => {
    return {uid: uid};
  });
});

exports.removeFromQueue =
    functions.https.onCall((data, context) => {
      const uid = context.auth.uid;
      return admin.database().ref("/queue").once("value", function(snap) {
        let queueCount = snap.child("queueCount").val();
        queueCount--;
        admin.database().ref("queue").update({wordCount: queueCount});
        admin.database().ref("/queue/players").child(uid).remove();
      }).then(() => {
        return {uid: uid};
      });
    });

exports.playerQueueListener =
    functions.database.ref("/queue/players/{pushId}/uid")
        .onCreate((snapshot, context) => {
          admin.database().ref("/queue").once("value", function(snap) {
            const playerCount = snap.child("queueCount").val() + 1;
            admin.database().ref("queue").update({
              queueCount: playerCount,
            });
            if (playerCount >= 4) {
              const newGroup = admin.database().ref("rooms").push().key;
              let counter = 0;
              const sentence = Math.floor((Math.random() * 10) + 1);
              admin.database().ref("rooms").child(newGroup)
                  .set({sentence: sentences[sentence]});
              snap.child("/players").forEach((childNode) => {
                const userId = childNode.key;
                admin.database().ref("/rooms")
                    .child(newGroup).child("/players").child(userId)
                    .set({wordCount: 0});
                admin.database().ref("/users").child(userId).update({
                  roomId: newGroup,
                });
                admin.database().ref("/queue/players").child(userId)
                    .remove();
                counter++;
                if (counter === 4) {
                  admin.database().ref("queue").update({
                    queueCount: playerCount - 4,
                  });
                }
              });
            }
          });
        });

const sentences = ["I said a hip hop, the hippie, the hippie to the hip, " +
  "hip hop, and you don't stop, the rockin' to the bang bang boogie, say," +
  " up jump the boogie, to the rhythm of the boogie, the beat.",
"The morning sun in all its glory greets the day with hope and comfort too." +
  " And you fill my life with laughter, you can make it better.",
"When the light of life has gone, no change for the meter." +
  " Then the king of spivs will come, selling blood by the litre." +
  " When nothing's sacred anymore, when the demon's knocking on your door," +
  " you'll still be staring down at the floor.",
"The morning sun in all its glory greets the day with hope and comfort too." +
  " And you fill my life with laughter, you can make it better.",
"When the light of life has gone, no change for the meter. " +
  "Then the king of spivs will come, selling blood by the litre." +
  " When nothing's sacred anymore, when the demon's knocking on your door," +
  " you'll still be staring down at the floor.",
"The morning sun in all its glory greets the day with hope and comfort too." +
  " And you fill my life with laughter, you can make it better.",
"When the light of life has gone, no change for the meter." +
  " Then the king of spivs will come, selling blood by the litre." +
  " When nothing's sacred anymore, when the demon's knocking on your door," +
  " you'll still be staring down at the floor.",
"The morning sun in all its glory greets the day with hope and comfort too." +
  " And you fill my life with laughter, you can make it better.",
"The morning sun in all its glory greets the day with hope and comfort too." +
  " And you fill my life with laughter, you can make it better.",
"When the light of life has gone, no change for the meter." +
  " Then the king of spivs will come, selling blood by the litre." +
  " When nothing's sacred anymore, when the demon's knocking on your door," +
  " you'll still be staring down at the floor.",
"The morning sun in all its glory greets the day with hope and comfort too." +
  " And you fill my life with laughter, you can make it better."];

