from Funcionario import Funcionario
from Empresa import Empresa

def main():
    #pedindo nome da empresa
    empresa_nome = input("Digite o nome da empresa:\n")
    empresa1 = Empresa(empresa_nome)
    
    #pedindo info funcionarios
    #funcionario 1
    print("")
    fun1 = input("Digite o nome do funcionário:\n")
    cargo1 = input("Digite o cargo deste funcionário:\n")
    sala1 = float(input("Digite o salário deste funcionário:\n"))
    #criando obj 'funcionario1'
    funcionario1 = Funcionario(fun1, cargo1, sala1)
    #Adicionando funcionario na empresa usando o método
    empresa1.add_funcionario(funcionario1)
    
    #funcionario 2
    print("")
    fun2 = input("Digite o nome do funcionário:\n")
    cargo2 = input("Digite o cargo deste funcionário:\n")
    sala2 = float(input("Digite o salário deste funcionário:\n"))
    #criando obj 'funcionario2'
    funcionario2 = Funcionario(fun2, cargo2, sala2)
    #Adicionando funcionario na empresa usando o método
    empresa1.add_funcionario(funcionario2)
    
    #funcionario 3
    print("")
    fun3 = input("Digite o nome do funcionário:\n")
    cargo3 = input("Digite o cargo deste funcionário:\n")
    sala3 = float(input("Digite o salário deste funcionário:\n"))
    #criando obj 'funcionario3'
    funcionario3 = Funcionario(fun3, cargo3, sala3)
    #Adicionando funcionario na empresa usando o método
    empresa1.add_funcionario(funcionario3)
    
    #exibindo funcionarios
    print("")
    empresa1.exibir_funcionarios()
    
    #Se quiser aumentar o valor do salario
    print("")
    opc = input("Você deseja atualizar o salário de algum funcionário?\n 1. Sim\n 2. Não\n")
    match opc:
        case "1":
            fun = input("Qual funcionário deseja alterar o salário:\n 1. Funcionário 1\n 2. Funcionário 2\n 3. Funcionário 3")
            match fun:
                case "1":
                    valor = float(input("Digite o salário atualizado:\n"))
                    funcionario1.aumentar_salario(valor)
                    
                    #exibindo funcionarios atualizados
                    print("")
                    print("Exibindo Informações atualizadas.")
                    empresa1.exibir_funcionarios()
                case "2":
                    valor = float(input("Digite o salário atualizado:\n"))
                    funcionario2.aumentar_salario(valor)
                    
                    #exibindo funcionarios atualizados
                    print("")
                    print("Exibindo Informações atualizadas.")
                    empresa1.exibir_funcionarios()
                case "3":
                    valor = float(input("Digite o salário atualizado:\n"))
                    funcionario3.aumentar_salario(valor)
                    
                    #exibindo funcionarios atualizados
                    print("")
                    print("Exibindo Informações atualizadas.")
                    empresa1.exibir_funcionarios()
                case _:
                    print("Opção inválida.")
        case "2":
            print("Ok, nenhum salário foi alterado.")
        case _:
            print("Opção inválida.")
    
#ativando o main
if __name__ == "__main__":
    main()