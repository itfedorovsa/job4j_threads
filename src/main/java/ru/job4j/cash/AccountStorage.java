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
        boolean added = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            added = true;
        }
        return added;
    }

    public synchronized boolean update(Account account) {
        boolean updated = false;
        if (accounts.containsKey(account.id())) {
            accounts.replace(account.id(), account);
            updated = true;
        }
        return updated;
    }

    public synchronized boolean delete(int id) {
        boolean deleted = false;
        if (accounts.containsKey(id)) {
            accounts.remove(id);
            deleted = true;
        }
        return deleted;
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
        if (from.isEmpty()) {
            throw new IllegalArgumentException("Outgoing account is not exist!");
        } else if (to.isEmpty()) {
            throw new IllegalArgumentException("Incoming account is not exist!");
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
