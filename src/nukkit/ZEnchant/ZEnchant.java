package nukkit.ZEnchant;

import java.util.HashMap;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class ZEnchant extends PluginBase implements Listener {

	public HashMap<String, Integer> ent1 = new HashMap<String, Integer>();
	public HashMap<String, Integer> ent2 = new HashMap<String, Integer>();
	public HashMap<String, Integer> ent3 = new HashMap<String, Integer>();

	public HashMap<String, Integer> entl1 = new HashMap<String, Integer>();
	public HashMap<String, Integer> entl2 = new HashMap<String, Integer>();
	public HashMap<String, Integer> entl3 = new HashMap<String, Integer>();

	public HashMap<String, Enchantment> plen = new HashMap<String, Enchantment>();

	public static int getRandomNumberInRange(int min, int max) {

		if (min == max) {
			return 1;
		} else {

			Random r = new Random();
			return r.nextInt((max - min) + 1) + min;
		}
	}

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info(TextFormat.LIGHT_PURPLE + "附魔插件 [ZeroK製作]");
	}

	@EventHandler
	public void PlayerTouch(PlayerInteractEvent event) {
		if (!event.isCancelled()) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (event.getBlock().getId() == 145) {
					FormWindowSimple window = new FormWindowSimple("鐵砧", "請選擇您要進行的動作(花費5等經驗):");
					window.addButton(new ElementButton("修復手中工具"));
					event.getPlayer().showFormWindow(window);
					event.setCancelled();
				}
				if (event.getBlock().getId() == 116) {
					this.sendEnchantmentList(event.getPlayer());
					event.setCancelled();
				}
			}
		}
	}

	@EventHandler
	public void formRespond(PlayerFormRespondedEvent event) {
		Player player = event.getPlayer();
		FormWindow window = event.getWindow();
		if (window instanceof FormWindowSimple) {
			String title = ((FormWindowSimple) event.getWindow()).getTitle();
			if (title.equals("鐵砧")) {
				if (event.wasClosed()) {
					return;
				}
				String button = ((FormResponseSimple) event.getResponse()).getClickedButton().getText();
				if (button.equals("修復手中工具")) {
					Item item = player.getInventory().getItemInHand();
					if (item.isTool()) {
						if (player.getExperienceLevel() < 5) {
							player.sendMessage("§8[§cX§8]§f 您沒有足夠的等級進行修復");
							return;
						}
						player.setExperience(player.getExperience(), player.getExperienceLevel() - 5);
						item.setDamage(0);
						player.getInventory().setItemInHand(item);
						player.sendMessage("§8[§e!§8]§f 成功修復手中工具 §e(花費5等經驗)");
					} else {
						player.sendMessage("§8[§cX§8]§f 您手中的物品並不是工具");
					}
				}
			}
			if (title.equals("附魔台")) {
				if (event.wasClosed()) {
					return;
				}
				String button = ((FormResponseSimple) event.getResponse()).getClickedButton().getText();
				Enchantment first = Enchantment.get(ent1.get(player.getName().toLowerCase()));
				Enchantment second = Enchantment.get(ent2.get(player.getName().toLowerCase()));
				Enchantment third = Enchantment.get(ent3.get(player.getName().toLowerCase()));
				Item item = player.getInventory().getItemInHand();
				if (button.equals(first.getName() + " " + entl1.get(player.getName().toLowerCase()))) {
					if (player.getExperienceLevel() < entl1.get(player.getName().toLowerCase()) * 10) {
						player.sendMessage("§8[§cX§8]§f 您沒有足夠的等級進行附魔");
						return;
					}
					Item blue = new Item(351, 4, entl1.get(player.getName().toLowerCase()));
					if (!player.getInventory().contains(blue)) {
						player.sendMessage("§8[§cX§8]§f 您沒有足夠的青金石進行附魔");
						return;
					}
					if (item.hasEnchantments()) {
						player.sendMessage("§8[§cX§8]§f 您手中的物品已有附魔效果");
						return;
					}
					first.setLevel(entl1.get(player.getName().toLowerCase()));
					plen.put(player.getName().toLowerCase(), first);
					this.sendEnchantConfirm(player);
				}
				if (button.equals(second.getName() + " " + entl2.get(player.getName().toLowerCase()))) {
					if (player.getExperienceLevel() < entl2.get(player.getName().toLowerCase()) * 10) {
						player.sendMessage("§8[§cX§8]§f 您沒有足夠的等級進行附魔");
						return;
					}
					Item blue = new Item(351, 4, entl2.get(player.getName().toLowerCase()));
					if (!player.getInventory().contains(blue)) {
						player.sendMessage("§8[§cX§8]§f 您沒有足夠的青金石進行附魔");
						return;
					}
					if (item.hasEnchantments()) {
						player.sendMessage("§8[§cX§8]§f 您手中的物品已有附魔效果");
						return;
					}
					second.setLevel(entl2.get(player.getName().toLowerCase()));
					plen.put(player.getName().toLowerCase(), second);
					this.sendEnchantConfirm(player);
				}
				if (button.equals(third.getName() + " " + entl3.get(player.getName().toLowerCase()))) {
					if (player.getExperienceLevel() < entl3.get(player.getName().toLowerCase()) * 10) {
						player.sendMessage("§8[§cX§8]§f 您沒有足夠的等級進行附魔");
						return;
					}
					Item blue = new Item(351, 4, entl2.get(player.getName().toLowerCase()));
					if (!player.getInventory().contains(blue)) {
						player.sendMessage("§8[§cX§8]§f 您沒有足夠的青金石進行附魔");
						return;
					}
					if (item.hasEnchantments()) {
						player.sendMessage("§8[§cX§8]§f 您手中的物品已有附魔效果");
						return;
					}
					third.setLevel(entl3.get(player.getName().toLowerCase()));
					plen.put(player.getName().toLowerCase(), third);
					this.sendEnchantConfirm(player);
				}
			}
		}
		if (window instanceof FormWindowModal) {
			String title = ((FormWindowModal) window).getTitle();
			FormResponseModal result = (FormResponseModal) event.getResponse();
			if (title.equals("是否確定要套用選定的附魔效果?")) {
				if (event.wasClosed()) {
					plen.remove(player.getName().toLowerCase());
					return;
				}
				if (result.getClickedButtonText().equals("取消")) {
					plen.remove(player.getName().toLowerCase());
				}
				if (result.getClickedButtonText().equals("確定")) {
					Item item = player.getInventory().getItemInHand();
					Item blue = new Item(351, 4, plen.get(player.getName().toLowerCase()).getLevel());
					int expl = plen.get(player.getName().toLowerCase()).getLevel() * 10;
					player.setExperience(player.getExperience(), player.getExperienceLevel() - expl);
					player.getInventory().removeItem(blue);
					item.addEnchantment(plen.get(player.getName().toLowerCase()));
					player.getInventory().setItemInHand(item);
					// new
					ent1.remove(player.getName().toLowerCase());
					ent2.remove(player.getName().toLowerCase());
					ent3.remove(player.getName().toLowerCase());
					entl1.remove(player.getName().toLowerCase());
					entl2.remove(player.getName().toLowerCase());
					entl3.remove(player.getName().toLowerCase());
					ent1.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(0, 32));
					ent2.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(0, 32));
					ent3.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(0, 32));
					Enchantment first_new = Enchantment.get(ent1.get(player.getName().toLowerCase()));
					Enchantment second_new = Enchantment.get(ent2.get(player.getName().toLowerCase()));
					Enchantment third_new = Enchantment.get(ent3.get(player.getName().toLowerCase()));
					entl1.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(first_new.getMinLevel(),
							first_new.getMaxEnchantableLevel()));
					entl2.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(second_new.getMinLevel(),
							second_new.getMaxEnchantableLevel()));
					entl3.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(third_new.getMinLevel(),
							third_new.getMaxEnchantableLevel()));
					plen.remove(player.getName().toLowerCase());
					// new
				}
			}
		}
	}

	public void sendEnchantmentList(Player player) {
		FormWindowSimple window = new FormWindowSimple("附魔台", "請選擇您要附魔的效果:");
		if (ent1.get(player.getName().toLowerCase()) == null) {
			ent1.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(0, 32));
			ent2.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(0, 32));
			ent3.put(player.getName().toLowerCase(), ZEnchant.getRandomNumberInRange(0, 32));
			Enchantment first = Enchantment.get(ent1.get(player.getName().toLowerCase()));
			Enchantment second = Enchantment.get(ent2.get(player.getName().toLowerCase()));
			Enchantment third = Enchantment.get(ent3.get(player.getName().toLowerCase()));
			entl1.put(player.getName().toLowerCase(),
					ZEnchant.getRandomNumberInRange(first.getMinLevel(), first.getMaxEnchantableLevel()));
			entl2.put(player.getName().toLowerCase(),
					ZEnchant.getRandomNumberInRange(second.getMinLevel(), second.getMaxEnchantableLevel()));
			entl3.put(player.getName().toLowerCase(),
					ZEnchant.getRandomNumberInRange(third.getMinLevel(), third.getMaxEnchantableLevel()));
			window.addButton(new ElementButton(first.getName() + " " + entl1.get(player.getName().toLowerCase())));
			window.addButton(new ElementButton(second.getName() + " " + entl2.get(player.getName().toLowerCase())));
			window.addButton(new ElementButton(third.getName() + " " + entl3.get(player.getName().toLowerCase())));
		} else {
			Enchantment first = Enchantment.get(ent1.get(player.getName().toLowerCase()));
			Enchantment second = Enchantment.get(ent2.get(player.getName().toLowerCase()));
			Enchantment third = Enchantment.get(ent3.get(player.getName().toLowerCase()));
			window.addButton(new ElementButton(first.getName() + " " + entl1.get(player.getName().toLowerCase())));
			window.addButton(new ElementButton(second.getName() + " " + entl2.get(player.getName().toLowerCase())));
			window.addButton(new ElementButton(third.getName() + " " + entl3.get(player.getName().toLowerCase())));
		}
		player.showFormWindow(window);
	}

	public void sendEnchantConfirm(Player player) {
		FormWindowModal window = new FormWindowModal("是否確定要套用選定的附魔效果?",
				"附魔效果: §e" + plen.get(player.getName().toLowerCase()).getName() + " "
						+ plen.get(player.getName().toLowerCase()).getLevel() + "\n§f附魔價格: §e"
						+ plen.get(player.getName().toLowerCase()).getLevel() * 10 + " §f等經驗, " + "青金石§e "
						+ plen.get(player.getName().toLowerCase()).getLevel() + " §f個",
				"確定", "取消");
		player.showFormWindow(window);
	}
}
