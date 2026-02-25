import os
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Configurar estilo visual
sns.set_theme(style="whitegrid")

# Crear carpeta para los gráficos
os.makedirs("charts", exist_ok=True)

print("Generando gráficos comparativos a partir de los datos CSV...")

# 1. Gráfico de Consumo de Memoria
try:
    df_mem = pd.read_csv("memory_results.csv")
    plt.figure(figsize=(10, 6))
    ax = sns.barplot(
        data=df_mem, 
        x="Density", 
        y="MemoryUsageMB", 
        hue="Implementation",
        palette="viridis"
    )
    plt.title("Consumo de Memoria en Heap (MB) por Implementación", fontsize=14, pad=15)
    plt.xlabel("Tipo de Grafo (10,000 Nodos)", fontsize=12)
    plt.ylabel("Uso de Memoria (MB)", fontsize=12)
    plt.legend(title="Estructura de Datos")
    
    # Agregar etiquetas con el valor encima de las barras
    for i in ax.containers:
        ax.bar_label(i, padding=3, fmt='%.1f MB', fontsize=9)

    plt.tight_layout()
    plt.savefig("charts/memory_comparison.png", dpi=300)
    plt.close()
    print("- Gráfico de memoria generado: charts/memory_comparison.png")
except Exception as e:
    print(f"Error procesando memory_results.csv: {e}")

# 2. Gráficos de Tiempo de Ejecución (BFS y DFS separadamente)
try:
    df_time = pd.read_csv("time_results.csv")
    df_time["TimeMS"] = df_time["TimeNS"] / 1_000_000.0  # Convertir a milisegundos para legibilidad
    
    for operation in ["BFS", "DFS"]:
        df_op = df_time[df_time["Operation"] == operation]
        
        plt.figure(figsize=(10, 6))
        ax = sns.barplot(
            data=df_op, 
            x="Density", 
            y="TimeMS", 
            hue="Implementation",
            palette="magma"
        )
        plt.title(f"Tiempo de Ejecución de {operation} (ms) por Implementación", fontsize=14, pad=15)
        plt.xlabel("Tipo de Grafo (10,000 Nodos)", fontsize=12)
        plt.ylabel("Tiempo de Ejecución (Milisegundos)", fontsize=12)
        plt.legend(title="Estructura de Datos")
        
        # Agregar etiquetas con el valor encima de las barras
        for i in ax.containers:
            ax.bar_label(i, padding=3, fmt='%.2f ms', fontsize=9)
            
        plt.tight_layout()
        plt.savefig(f"charts/time_{operation.lower()}_comparison.png", dpi=300)
        plt.close()
        print(f"- Gráfico de tiempo generado: charts/time_{operation.lower()}_comparison.png")
except Exception as e:
    print(f"Error procesando time_results.csv: {e}")

print("Proceso finalizado con éxito.")
