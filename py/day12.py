def e_convert(c):
    if c == 'S':
        return 'a'
    if c == 'E':
        return 'z'
    return c


def read_elevation_map():
    _map = []
    with open('C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt') as file:
        for line in file:
            line = line.rstrip()
            _map.append([ord(e_convert(c)) - ord('a') for c in line])
    return _map


emap = read_elevation_map()

print('\n'.join(str(s) for s in emap))


def build_adjacency_matrix(emap):
    n_rows = len(emap)
    n_cols = len(emap[0])
    _amatrix = {}

    for row in range(n_rows):
        for col in range(n_cols):
            node = f'{row}_{col}'
            # up to 4 neighbors: left, right, up, down
            for n_delta in (-1, 0), (1, 0), (0, 1), (0, -1):
                n_row, n_col = row + n_delta[1], col + n_delta[0]
                if n_row < 0 or n_col < 0 or n_row >= n_rows or n_col >= n_cols:
                    continue
                # check elevation
                if emap[n_row][n_col] > emap[row][col] + 1:
                    continue
                if node not in _amatrix:
                    _amatrix[node] = []
                _amatrix[node].append(f'{n_row}_{n_col}')

    return _amatrix


amatrix = build_adjacency_matrix(emap)


def to_graphviz(amatrix):
    print("digraph G {")
    for node in amatrix.keys():
        for adjacent in amatrix[node]:
            print(f'  "{node}" -> "{adjacent}";')
    print("}")


to_graphviz(amatrix)

# Step 2: Dijkstra

# Step 3: High-Five! ⭐!

print('\n\nAdjacency Matrix:')
print('\n'.join(str(s) for s in amatrix.items()))
