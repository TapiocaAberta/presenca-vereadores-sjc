# Esse documento ira baixar todos os arquivos que contem a informacao de presenca na camara de SJC
# Ele apaga tudo e baixa tudo novamente - entao cuidado!
rm -f links.txt
rm -rf arquivos
# ajustar o numero de paginas
for i in `seq 0 12` 
do 
    pg="http://www.camarasjc.sp.gov.br/portal-da-transparencia/presenca-de-vereadores.php?pg=$i"
    echo "Baixando links da pagina $pg" 
    links=$(curl -silent "$pg" | grep -o 'http://www.camarasjc.sp.gov.br/library/download.php?path=../arquivo/sessoes.*\.pdf\|http://www.camarasjc.sp.gov.br/library/download.php?path=../arquivo/sessoes.*\.xls')
    echo "$links" >> links.txt 
done

mkdir arquivos
cd arquivos
for l in `cat ../links.txt`
do 
        wget  $l 
done
cd -
