package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean transferred = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        int moneyFrom;
        int moneyTo;
        if (from.isEmpty() || to.isEmpty()) {
            return false;
        }
        moneyFrom = from.get().amount();
        moneyTo = to.get().amount();
        if (moneyFrom >= amount) {
            moneyFrom -= amount;
            moneyTo += amount;
            update(new Account(fromId, moneyFrom));
            update(new Account(toId, moneyTo));
            transferred = true;
        }
        return transferred;
    }
}
