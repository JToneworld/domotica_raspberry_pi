from firebase import firebase
import threading
import time

class Conexion(threading.Thread):

    def __init__(self, cb):
        threading.Thread.__init__(self)
        self.callback = cb
        self.fire = firebase.FirebaseApplication('https://android2016.firebaseio.com/', None)
        self.ultimo_estado = self.fire.get('/luces/sala', None)
        self.callback(self.ultimo_estado)

    def run(self):
		E = []
		E.append(self.ultimo_estado)
		i = 0
		
		while True:
			estado_actual = self.fire.get('/luces/sala', None)
			E.append(estado_actual)

			if E[i] != E[-1]:
				self.callback(estado_actual)
			
			del E[0]
			i = i+i
			time.sleep(0.3)
