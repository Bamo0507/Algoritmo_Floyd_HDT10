import networkx as nx

def leer_grafo(archivo):
    G = nx.DiGraph()
    with open(archivo, 'r') as f:
        next(f)  # Saltar la primera línea que contiene el encabezado
        for linea in f:
            ciudad1, ciudad2, distancia = linea.strip().split()
            G.add_edge(ciudad1, ciudad2, weight=int(distancia))
    return G

grafo = leer_grafo('Programa_Python\guategrafoPyhon.txt')

def encontrar_centro_grafo(G):
    distancias = dict(nx.floyd_warshall(G))
    excentricidades = {nodo: max(distancias[nodo].values()) for nodo in G.nodes()}
    centro = min(excentricidades, key=excentricidades.get)
    return centro

def conectar_ciudades(grafo, ciudad_origen, ciudad_destino, distancia, archivo):
    grafo.add_edge(ciudad_origen, ciudad_destino, weight=distancia)
    with open(archivo, 'a') as f:
        f.write(f"\n{ciudad_origen} {ciudad_destino} {distancia}")

def desconectar_ciudades(grafo, ciudad_origen, ciudad_destino, archivo):
    if ciudad_origen not in grafo or ciudad_destino not in grafo:
        print("Al menos una de las ciudades especificadas no existe en el grafo.")
        return
    if grafo.has_edge(ciudad_origen, ciudad_destino):
        grafo.remove_edge(ciudad_origen, ciudad_destino)
        with open(archivo, 'r') as f:
            lineas = f.readlines()
        with open(archivo, 'w') as f:
            for linea in lineas:
                if not linea.startswith(f"{ciudad_origen} {ciudad_destino}"):
                    f.write(linea)
    else:
        print("No existe una conexión entre las ciudades especificadas.")

def modificar_grafo(grafo, nombre_archivo):
    while True:
        opcion_modificacion = input("Seleccione la opción deseada:\n1. Conectar ciudades.\n2. Desconectar ciudades.\n3. Volver al menú principal.\nOpción: ")
        if opcion_modificacion == '1':
            ciudad_origen = input("Ingrese la ciudad de origen: ")
            ciudad_destino = input("Ingrese la ciudad de destino: ")
            distancia = int(input("Ingrese la distancia entre las ciudades: "))
            conectar_ciudades(grafo, ciudad_origen, ciudad_destino, distancia, nombre_archivo)
            print(f"Se ha conectado {ciudad_origen} con {ciudad_destino}.")
        elif opcion_modificacion == '2':
            ciudad_origen = input("Ingrese la ciudad de origen: ")
            ciudad_destino = input("Ingrese la ciudad de destino: ")
            desconectar_ciudades(grafo, ciudad_origen, ciudad_destino, nombre_archivo)
            print(f"Se ha desconectado {ciudad_origen} de {ciudad_destino}.")
        elif opcion_modificacion == '3':
            break
        else:
            print("Opción inválida. Por favor, ingrese una opción válida.")

def interfaz_usuario(grafo):
    while True:
        opcion = input("Ingrese la opción deseada:\n1. Calcular ruta más corta entre ciudades.\n2. Encontrar el centro del grafo.\n3. Modificar el grafo.\n4. Salir\nOpción: ")
        if opcion == '1':
            ciudad_origen = input("Ingrese la ciudad de origen: ")
            ciudad_destino = input("Ingrese la ciudad de destino: ")
            if ciudad_origen not in grafo or ciudad_destino not in grafo:
                print("Al menos una de las ciudades especificadas no existe en el grafo.")
            else:
                try:
                    distancia = nx.shortest_path_length(grafo, source=ciudad_origen, target=ciudad_destino, weight='weight')
                    ruta = nx.shortest_path(grafo, source=ciudad_origen, target=ciudad_destino, weight='weight')
                    print(f"La distancia más corta entre {ciudad_origen} y {ciudad_destino} es {distancia} KM.")
                    print("Ruta:", ruta)
                except nx.NetworkXNoPath:
                    print("No hay una ruta entre las ciudades especificadas.")
        elif opcion == '2':
            centro = encontrar_centro_grafo(grafo)
            print("El centro del grafo es:", centro)
        elif opcion == '3':
            modificar_grafo(grafo, "Programa_Python\guategrafoPyhon.txt")
        elif opcion == '4':
            print("Que tenga un buen día querido usuario!!!")
            break
        else:
            print("Opción inválida. Por favor, ingrese una opción válida.")

interfaz_usuario(grafo)
