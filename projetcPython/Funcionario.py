class Funcionario:
    
    #bob construtor c/ parametros
    def __init__(self, nome, cargo, salario):
        #Atributos dessa classe
        self.nome = nome
        self.cargo = cargo
        self.salario = salario
    
    
    #exibir info
    def exibir_info(self):
        print(f"Informações do funcionário:\nNome: {self.nome}\nCargo: {self.cargo}\nSalário: {self.salario}")
        
    #aumento de salario
    def aumentar_salario(self, percentual):
        self.salario += self.salario * (percentual / 100)
        print(f"Sáliro do {self.nome} foi atualiazado para R${self.salario}.")