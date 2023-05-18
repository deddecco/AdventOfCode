import re

print("Hello, World!")

level = 0

path = []
sizes = {}


def path_str(p):
    return f'/{"/".join(p)}'


with open("../big-elf-input.txt") as file:
    for line in file:
        if re.match(r'\$ ls', line):
            print(f'{"  " * level}LS COMMAND!')
        elif re.match(r'\$ cd', line):
            cd_dir = re.search(r'\$ cd (.+)', line).group(1)
            if cd_dir == '/':
                level = 0
                path = []
            elif cd_dir == '..':
                level -= 1
                sizes[path_str(path[:-1])] += sizes[path_str(path)]
                path = path[:-1]
            else:
                level += 1
                path.append(cd_dir)
                sizes[path_str(path)] = 0

            print(f'{"  " * level}CD COMMAND! {cd_dir}, path: {path_str(path)}')
        else:
            print(f'This must be a file OR a directory! 👉 {line.rstrip()}')
            if re.match(r'^\d+', line):
                ps = path_str(path)
                size = re.search(r'^(\d+)', line).group(1)
                print(f'SIZE: {size}')
                if ps not in sizes:
                    sizes[ps] = 0
                sizes[ps] += int(size)

print(f'sizes: {sizes}')

total_left = sizes['/'] - 40000000
print(f'Total left: {total_left}')
kv_pairs = sorted(([e[1], e[0]] for e in sizes.items() if e[1] >= total_left), key=lambda x: x[0])

print(f'Delete: {kv_pairs}')

totals = [s for s in sizes.values() if s <= 100000]

total_size = sum(totals)
print(f'Totals: {sizes.values()}')
print(f'total_size: {total_size}')
