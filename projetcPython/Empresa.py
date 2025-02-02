from Funcionario import Funcionario

class Empresa:
    #bob construtor c/ parametro
    def __init__(self, nome):
        self.nome = nome
        self.list_funcionarios = []
        
    #add funcionario
    def add_funcionario(self, funcionario):
        self.list_funcionarios.append(funcionario)
        print(f"Funcionário {funcionario.nome} adicionado com sucesso!")
    
    #exibe a lista
    def exibir_funcionarios(self):
        print(f"Funcionários da empresa {self.nome}")
        for funcionarios in self.list_funcionarios:
            funcionarios.exibir_info()