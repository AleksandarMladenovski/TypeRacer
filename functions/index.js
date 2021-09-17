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
      return admin.database().ref("/queue/players").child(uid).remove()
          .then(() => {
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
              snap.child("/players").forEach((childNode) => {
                const userId = childNode.key;
                admin.database().ref("rooms")
                    .child(newGroup).child(userId).set({wordCount: 0});
                admin.database().ref("/users").child(userId).update({
                  roomId: newGroup,
                });
                admin.database().ref("/queue/players").child(userId).remove();
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


