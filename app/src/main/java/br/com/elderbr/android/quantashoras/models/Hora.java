package br.com.elderbr.android.quantashoras.models;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import br.com.elderbr.android.quantashoras.utils.Msg;

public class Hora {

    private final Calendar data = Calendar.getInstance(new Locale("pt", "BR"));
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private ContentValues values = new ContentValues();

    public Hora() {
    }

    public Hora(String horas) {
        if (horas == null || horas.isEmpty()) {
            throw new RuntimeException("A hora não pode ser vazia!");
        }
        if (horas.contains(":")) {
            if (horas.length() == 5 && horas.split(":").length < 2) {
                throw new RuntimeException("Hora invalido \"" + horas + "\"!");
            }
            if (horas.length() >= 3 && horas.split(":").length == 2) {

                String hr = horas.split(":")[0];
                String minuto = horas.split(":")[1];

                if (hr == null || hr.isEmpty()) {
                    throw new RuntimeException("Hora invalido \"" + horas + "\"!");
                }

                if (minuto == null || minuto.isEmpty()) {
                    throw new RuntimeException("Minutos das horas invalido \"" + horas + "\"!");
                }

                try {
                    data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hr));
                    data.set(Calendar.MINUTE, Integer.parseInt(minuto));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Horário invalido \"" + horas + "\"!");
                }
            }
            if(horas.split(":").length == 1){
                String hr = horas.split(":")[0];
                data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hr));
                data.set(Calendar.MINUTE, 0);
            }
        } else {
            if (horas.length() == 5) {
                throw new RuntimeException("Horário invalido \"" + horas + "\"!");
            }
            try {
                if(horas.length()==2) {
                    data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horas));
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Erro ao pegar o Horário \"" + horas + "\"!");
            }
        }
    }

    public Hora(Hora horas) {
        data.set(Calendar.HOUR_OF_DAY, horas.getHora());
        data.set(Calendar.MINUTE, horas.getMinutos());
    }

    public Hora(int hora, int minutos) {
        data.set(Calendar.HOUR_OF_DAY, hora);
        data.set(Calendar.MINUTE, minutos);
    }

    public Hora setHora(int hora) {
        data.set(Calendar.HOUR_OF_DAY, hora);
        return this;
    }

    public Hora setHora(int hora, int minutos) {
        data.set(Calendar.MINUTE, minutos);
        data.set(Calendar.HOUR_OF_DAY, hora);
        return this;
    }

    public Hora setHora(String hora) {
        parse(hora);
        return this;
    }

    public Hora setHora(Hora hora) {
        data.set(Calendar.HOUR_OF_DAY, hora.getHora());
        data.set(Calendar.MINUTE, hora.getMinutos());
        return this;
    }

    public int getHora() {
        return data.get(Calendar.HOUR_OF_DAY);
    }

    public Hora setMinutos(int minutos) {
        data.set(Calendar.MINUTE, minutos);
        return this;
    }

    public Hora setMinutos(String minutos) {
        data.set(Calendar.MINUTE, Integer.parseInt(minutos));
        return this;
    }

    public Hora subHoraMinuto(int hora, int minutos) {
        data.add(Calendar.MINUTE, -minutos);
        data.add(Calendar.HOUR, -hora);
        return this;
    }

    public double getDoubleHora() {
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int minutos = data.get(Calendar.MINUTE);
        if (minutos < 10) {
            return Double.parseDouble(hora + ".0" + minutos);
        }
        return Double.parseDouble(hora + "." + minutos);
    }

    public int getMinutos() {
        return data.get(Calendar.MINUTE);
    }

    public Hora subtrair(Hora hora) {
        if (getHora() < hora.getHora()) {
            hora.addDia(1);
        }
        data.add(Calendar.MINUTE, -hora.getMinutos());
        data.add(Calendar.HOUR_OF_DAY, -hora.getHora());
        return this;
    }

    public Hora subtrair(int hora, int minutos) {
        data.add(Calendar.MINUTE, -minutos);
        data.add(Calendar.HOUR_OF_DAY, -hora);
        return this;
    }

    public Hora somar(Hora hora) {
        data.add(Calendar.MINUTE, hora.getMinutos());
        data.add(Calendar.HOUR_OF_DAY, hora.getHora());
        return this;
    }

    public Hora somar(String horas) {
        if (horas == null || horas.isEmpty()) {
            throw new RuntimeException("O horario não pode ser vazio!!!");
        }
        if (horas.contains(":")) {
            if (horas.split(":").length == 2) {
                String hr = horas.split(":")[0];
                String minuto = horas.split(":")[1];

                if (hr == null || hr.isEmpty()) {
                    throw new RuntimeException("Digite a hora, não pode ser vazia!");
                }
                if (minuto == null || minuto.isEmpty()) {
                    throw new RuntimeException("Digite o minutos, não pode ser vazia!");
                }
                try {
                    data.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hr));
                    data.add(Calendar.MINUTE, Integer.parseInt(minuto));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Erro ao pegar a hora!!!");
                }
            }
        } else {
            try {
                data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horas));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Erro ao pegar a hora " + horas + " - Erro: " + e.getMessage());
            }
        }
        return this;
    }


    public String toHoras() {
        return format.format(data.getTime());
    }


    public Hora parse(String hora) {
        data.set(Calendar.HOUR_OF_DAY, 0);
        data.set(Calendar.MINUTE, 0);
        if (hora == null || !hora.contains(":")) {
            return this;
        }

        String[] hrs = hora.split(":");

        if (hrs[0] == null || hrs[1] == null) {
            return this;
        }

        int h = Integer.parseInt(hrs[0]);
        int m = Integer.parseInt(hrs[1]);

        data.set(Calendar.HOUR_OF_DAY, h);
        data.set(Calendar.MINUTE, m);

        return this;
    }

    public int getDiaAno() {
        return data.get(Calendar.DAY_OF_YEAR);
    }

    public String toDia() {
        return "" + data.get(Calendar.DAY_OF_MONTH);
    }

    public Hora addDia(int dia) {
        data.add(Calendar.DAY_OF_YEAR, dia);
        return this;
    }

    public ContentValues getValues() {
        values.put(getClass().getSimpleName().toLowerCase(), toHoras());
        return values;
    }

    public String toClass() {
        return getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String toString() {
        return toHoras();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this != o) return false;
        Hora hora = (Hora) o;
        return Objects.equals(data.getTime(), hora.data.getTime());
    }
}
